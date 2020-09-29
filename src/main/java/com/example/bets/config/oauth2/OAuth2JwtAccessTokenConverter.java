package com.example.bets.config.oauth2;

import com.example.bets.config.AppProperties;
import com.example.bets.security.oauth2.OAuth2SignatureVerifierClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;

/**
 * Improved {@link JwtAccessTokenConverter} that can handle lazy fetching of public verifier keys.
 */
@Slf4j
public class OAuth2JwtAccessTokenConverter extends JwtAccessTokenConverter {

    private final AppProperties properties;
    private final OAuth2SignatureVerifierClient oAuth2SignatureVerifierClient;
    /**
     * When did we last fetch the public key?
     */
    private long lastKeyFetchTimestamp;


    public OAuth2JwtAccessTokenConverter(AppProperties properties,
                                         OAuth2SignatureVerifierClient oAuth2SignatureVerifierClient) {
        this.properties = properties;
        this.oAuth2SignatureVerifierClient = oAuth2SignatureVerifierClient;
        // NOTE, it is very important to try fetch for the public key to verify the JWT signature
        tryCreateSignatureVerifier();
    }

    /**
     * Try to decode the token with the current public key.
     * If it fails, contact the OAuth2 server to get a new public key, then try again.
     * We might not have fetched it in the first place or it might have changed.
     *
     * @param token the JWT token to decode.
     * @return the resulting claims.
     * @throws InvalidTokenException if we cannot decode the token.
     */
    @Override
    protected Map<String, Object> decode(String token) {
        try {
            //check if our public key and thus SignatureVerifier have expired
            long ltt = properties.getSignatureVerification().getTtl();
            if (ltt > 0 && System.currentTimeMillis() - lastKeyFetchTimestamp > ltt) {
                throw new InvalidTokenException("public key expired");
            }
            return super.decode(token);
        } catch (InvalidTokenException e) {
            if (tryCreateSignatureVerifier()) {
                super.decode(token);
            }
            throw e;
        }
    }

    /**
     * Fetch a new public key from the AuthorizationServer.
     *
     * @return true, if we could fetch it; false, if we could not.
     */
    private boolean tryCreateSignatureVerifier() {
        long time = System.currentTimeMillis();
        if (time - lastKeyFetchTimestamp < properties.getSignatureVerification().getPublicKeyRefreshRateLimit()) {
            return false;
        }
        try {
            SignatureVerifier verifier = oAuth2SignatureVerifierClient.getSignatureVerifier();
            if (verifier != null) {
                lastKeyFetchTimestamp = time;
                setVerifier(verifier);
                log.debug("Public key retrieved from OAuth2 server to create SignatureVerifier");
                return true;
            }
        } catch (Exception ex) {
            log.error("Could not get public key from Authorization server to create SignatureVerifier", ex);
        }
        return false;
    }

    /**
     * Extract JWT claims and set it to OAuth2Authentication decoded details.
     * Here is how to get details:
     *
     * <pre>
     * <code>
     *  SecurityContext securityContext = SecurityContextHolder.getContext();
     *  Authentication authentication = securityContext.getAuthentication();
     *  if (authentication != null) {
     *      Object details = authentication.getDetails();
     *      if (details instanceof OAuth2AuthenticationDetails) {
     *          Object decodedDetails = ((OAuth2AuthenticationDetails) details).getDecodedDetails();
     *          if (decodedDetails != null &amp;&amp; decodedDetails instanceof Map) {
     *             String detailFoo = ((Map) decodedDetails).get("foo");
     *          }
     *      }
     *  }
     * </code>
     *  </pre>
     * @param claims OAuth2JWTToken claims.
     * @return {@link OAuth2Authentication}.
     */
    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {
        OAuth2Authentication authentication = super.extractAuthentication(claims);
        authentication.setDetails(claims);
        return authentication;
    }
}
