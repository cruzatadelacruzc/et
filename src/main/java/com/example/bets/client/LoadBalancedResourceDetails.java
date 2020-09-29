package com.example.bets.client;

import com.example.bets.config.AppProperties;
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

    private final AppProperties properties;

    private LoadBalancerClient loadBalancerClient;

    public LoadBalancedResourceDetails(AppProperties properties, LoadBalancerClient loadBalancerClient) {
        this.properties = properties;
        this.loadBalancerClient = loadBalancerClient;
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

    @Override
    public String getAccessTokenUri() {
        if (loadBalancerClient != null) {
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
