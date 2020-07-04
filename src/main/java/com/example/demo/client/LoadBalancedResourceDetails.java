package com.example.demo.client;

import com.example.demo.config.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * <p>LoadBalancedResourceDetails class.</p>
 */
@Slf4j
@Component
public class LoadBalancedResourceDetails extends ClientCredentialsResourceDetails {

    private String tokenServiceId;

    private final AppProperties properties;

    private LoadBalancerClient loadBalancerClient;

    public LoadBalancedResourceDetails(AppProperties properties, LoadBalancerClient loadBalancerClient) {
        this.properties = properties;
        this.loadBalancerClient = loadBalancerClient;
    }

    @Override
    public String getClientId() {
        String clientId = properties.getSecurity().getClientAuthorization().getClientId();
        if (clientId == null) {
            throw new InvalidClientException(
                    "No client-authorization.client-id configured in application properties"
            );
        }
        return clientId;
    }

    @Override
    public String getClientSecret() {
        String clientSecret = properties.getSecurity().getClientAuthorization().getClientSecret();
        if (clientSecret == null) {
            throw new InvalidClientException(
                    "No client-authorization.client-secret configured in application properties"
            );
        }
        return clientSecret;
    }

    public String getTokenServiceId() {
        String tokenServiceId = properties.getSecurity().getClientAuthorization().getTokenServiceId();
        if (tokenServiceId == null) {
            throw new InvalidClientException(
                    "No client-authorization.token-service-id configured in application properties"
            );
        }
        return tokenServiceId;
    }

    public LoadBalancedResourceDetails setTokenServiceId(String tokenServiceId) {
        this.tokenServiceId = tokenServiceId;
        return this;
    }

    @Override
    public String getAccessTokenUri() {
        if (loadBalancerClient != null && tokenServiceId != null && !tokenServiceId.isEmpty()) {
            try {
                String accessTokenUri = properties.getSecurity().getClientAuthorization().getAccessTokenUri();
                if (accessTokenUri == null) {
                    throw new InvalidClientException(
                            "No client-authorization.access-token-uri configured in application properties"
                    );
                }
                return loadBalancerClient.reconstructURI(
                        loadBalancerClient.choose(getTokenServiceId()),
                        new URI(accessTokenUri)
                ).toString();
            } catch (URISyntaxException ex) {
                log.error("Returning an invalid URI: {}", ex.getMessage());
                return super.getAccessTokenUri();
            }
        } else {
            return super.getAccessTokenUri();
        }
    }
}
