package com.example.demo.config;

import com.example.demo.client.LoadBalancedResourceDetails;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;

@Configuration
@EnableFeignClients(basePackages = "com.example.demo")
@Import(FeignClientsConfiguration.class)
public class FeignConfiguration {

    private final AppProperties properties;

    public FeignConfiguration(AppProperties properties) {
        this.properties = properties;
    }

    /**
     * Set the Feign specific log level to log client REST requests.
     */
    @Bean
    feign.Logger.Level feignLoggerLevel() {
        return feign.Logger.Level.BASIC;
    }

    public String getClientId() {
        String clientId = properties.getSecurity().getClientAuthorization().getClientId();
        if (clientId == null) {
            throw new InvalidClientException(
                    "No client-authorization.client-id configured in application properties"
            );
        }
        return clientId;
    }

    public String getClientSecret() {
        String clientSecret = properties.getSecurity().getClientAuthorization().getClientSecret();
        if (clientSecret == null) {
            throw new InvalidClientException(
                    "No client-authorization.client-secret configured in application properties"
            );
        }
        return clientSecret;
    }

    @Bean
    public LoadBalancedResourceDetails loadBalancedResourceDetails(LoadBalancerClient loadBalancerClient) {
        LoadBalancedResourceDetails loadBalancedResourceDetails = new LoadBalancedResourceDetails(properties,loadBalancerClient);

        loadBalancedResourceDetails.setClientId(getClientId());
        loadBalancedResourceDetails.setClientSecret(getClientSecret());
        return loadBalancedResourceDetails;
    }
}
