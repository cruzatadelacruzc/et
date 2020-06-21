package com.example.demo.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

@Getter
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class AppProperties {
    private Security security = new Security();
    private ClientApp clientApp = new ClientApp();
    private final CorsConfiguration cors = new CorsConfiguration();
    private SignatureVerification signatureVerification = new SignatureVerification();

    static class ClientApp {
        private String name;

        public ClientApp() {
            this.name = "betApp";
        }

        public String getName() {
            return name;
        }

        public ClientApp setName(String name) {
            this.name = name;
            return this;
        }
    }
    @Getter
    public static class SignatureVerification {
        /**
         * Maximum refresh rate for public keys in ms.
         * We won't fetch new public keys any faster than that to avoid spamming UAA in case
         * we receive a lot of "illegal" tokens.
         */
        private long publicKeyRefreshRateLimit = 10 * 1000L;
        /**
         * Maximum TTL for the public key in ms.
         * The public key will be fetched again from UAA if it gets older than that.
         * That way, we make sure that we get the newest keys always in case they are updated there.
         */
        private long ttl = 24 * 60 * 60 * 1000L;
        /**
         * Endpoint where to retrieve the public key used to verify token signatures.
         */
        private String publicKeyEndpointUri = "http://uaa/oauth/token_key";

        public SignatureVerification setPublicKeyRefreshRateLimit(long publicKeyRefreshRateLimit) {
            this.publicKeyRefreshRateLimit = publicKeyRefreshRateLimit;
            return this;
        }

        public SignatureVerification setTtl(long ttl) {
            this.ttl = ttl;
            return this;
        }

        public SignatureVerification setPublicKeyEndpointUri(String publicKeyEndpointUri) {
            this.publicKeyEndpointUri = publicKeyEndpointUri;
            return this;
        }
    }
    @Getter
    public static class Security {

        private final ClientAuthorization clientAuthorization = new ClientAuthorization();

        @Data
        public static class ClientAuthorization {

            private String accessTokenUri = "http://uaa/oauth/token";

            private String tokenServiceId = "uaa";

            private String clientId = "internal";

            private String clientSecret = "internal";
        }
    }
}
