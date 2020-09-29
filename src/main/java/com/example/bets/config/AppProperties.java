package com.example.bets.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;
/**
 * Properties specific to Bets.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 */
@Getter
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class AppProperties {
    private final Cache cache = new Cache();
    private Security security = new Security();
    private ClientApp clientApp = new ClientApp();
    private final CorsConfiguration cors = new CorsConfiguration();
    private final RegistryConfig registryConfig = new RegistryConfig();
    private SignatureVerification signatureVerification = new SignatureVerification();

    @Getter
    public static class Cache{
        private int timeToLiveSeconds = 3600;
        private int backupCount = 1;
        private final ManagementCenter managementCenter = new ManagementCenter();
        @Getter
        public static class ManagementCenter {
            private boolean enabled = false;
            private int updateInterval = 3;
            private String url ="";

            public ManagementCenter setEnabled(boolean enabled) {
                this.enabled = enabled;
                return this;
            }

            public ManagementCenter setUpdateInterval(int updateInterval) {
                this.updateInterval = updateInterval;
                return this;
            }

            public ManagementCenter setUrl(String url) {
                this.url = url;
                return this;
            }
        }
    }
    @Getter
    public static class ClientApp {
        private String name;

        public ClientApp() {
            this.name = "betApp";
        }

        public ClientApp setName(String name) {
            this.name = name;
            return this;
        }
    }
    @Getter
    public static class Security {

        private final ClientAuthorization clientAuthorization = new ClientAuthorization();

        @Data
        public static class ClientAuthorization {

            private String accessTokenUri = "http://uaa/oauth/token";

            private String tokenServiceId = "bets";

            private String clientId = "internal";

            private String clientSecret = "internal";
        }
    }
    @Getter
    public static class RegistryConfig {
        private String password;

        public RegistryConfig setPassword(String password) {
            this.password = password;
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

}
