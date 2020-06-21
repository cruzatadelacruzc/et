package com.example.demo.config.oauth2;

import com.example.demo.config.AppProperties;
import com.example.demo.security.oauth2.OAuth2SignatureVerifierClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Client fetching the public key from UAA to create a {@link SignatureVerifier}.
 */
@Slf4j
@Component
public class OAuth2UaaSignatureVerifierClient implements OAuth2SignatureVerifierClient {

    private final AppProperties properties;
    private final RestTemplate restTemplate;

    public OAuth2UaaSignatureVerifierClient(DiscoveryClient discoveryClient,
                                            AppProperties properties,
                                            @Qualifier("loadBalancedRestTemplate") RestTemplate restTemplate) {
        this.properties = properties;
        this.restTemplate = restTemplate;
        // Load available UAA servers
        discoveryClient.getServices();
    }

    /**
     * Fetches the public key from the UAA.
     *
     * @return the public key used to verify JWT tokens; or {@code null}.
     */
    @Override
    public SignatureVerifier getSignatureVerifier() {
        try {
        HttpEntity<Void> request = new HttpEntity<Void>(new HttpHeaders());
         String key = (String) restTemplate
                 .exchange(getPublicKeyEndpoint(), HttpMethod.GET,request, Map.class)
                 .getBody()
                 .get("value");
         return new RsaVerifier(key);
        } catch (IllegalStateException ex) {
            log.warn("could not contact UAA to get public key");
            return null;
        }
    }

    /**
     * Returns the configured endpoint URI to retrieve the public key.
     *
     * @return the configured endpoint URI to retrieve the public key.
     */
    private String getPublicKeyEndpoint() {
        String tokenEndpointUrl = properties.getSignatureVerification().getPublicKeyEndpointUri();
        if (tokenEndpointUrl == null) {
            throw new InvalidClientException("no token endpoint configured in application properties");
        }
        return tokenEndpointUrl;
    }
}
