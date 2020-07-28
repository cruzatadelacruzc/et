package com.example.demo.config;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

@Slf4j
@Configuration
@EnableCaching
public class CacheConfiguration implements DisposableBean {

    private final Environment env;

    @Value("${spring.application.name}")
    private String instanceName;

    private final DiscoveryClient discoveryClient;

    private final ServerProperties serverProperties;

    private static final Integer PORT = 5701;

    private Registration registration;

    public CacheConfiguration(Environment env, DiscoveryClient discoveryClient, ServerProperties serverProperties) {
        this.env = env;
        this.discoveryClient = discoveryClient;
        this.serverProperties = serverProperties;
    }

    @Autowired(required = false)
    public void setRegistration(Registration registration) {
        this.registration = registration;
    }


    @Override
    public void destroy() throws Exception {
        log.info("Closing Cache Manager");
        Hazelcast.shutdownAll();
    }

    @Bean
    public CacheManager cacheManager(HazelcastInstance hazelcastInstance) {
        log.debug("Starting HazelcastCacheManager");
        return new HazelcastCacheManager(hazelcastInstance);
    }

    @Bean
    public HazelcastInstance hazelcastInstance(AppProperties properties) {
        log.debug("Configuring Hazelcast");
        HazelcastInstance hazelcastInstance = Hazelcast.getHazelcastInstanceByName(this.instanceName);
        if (hazelcastInstance != null) {
            log.debug("Hazelcast already initialized");
            return hazelcastInstance;
        }

        Config config = new Config();
        config.setInstanceName(this.instanceName);
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        if (this.registration == null) {
            log.warn("No discovery service is set up, Hazelcast cannot create a cluster.");
        } else {
            // The serviceId is by default the application's name,
            // see the "spring.application.name" standard Spring property
            String serviceId = registration.getServiceId();
            log.debug("Configuring Hazelcast clustering for instanceId: {}", serviceId);
            // In development, everything goes through 127.0.0.1, with a different port
            if (env.acceptsProfiles(Profiles.of(Constants.PROFILE_DEV))) {
                log.debug("Application is running with the \"dev\" profile, Hazelcast " +
                        "cluster will only work with localhost instances");
                System.setProperty("hazelcast.local.localAddress", "127.0.0.1");
                config.getNetworkConfig().setPort(serverProperties.getPort() + PORT);
                config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
                for (ServiceInstance instance : discoveryClient.getInstances(serviceId)) {
                    String clusterMember = "127.0.0.1:" + (instance.getPort() + PORT);
                    log.debug("Adding Hazelcast (dev) cluster member {}", clusterMember);
                    config.getNetworkConfig().getJoin().getTcpIpConfig().addMember(clusterMember);
                }
            } else { // Production configuration, one host per instance all using port 5701
                config.getNetworkConfig().setPort(PORT);
                config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
                for (ServiceInstance instance : discoveryClient.getInstances(serviceId)) {
                    String clusterMember = instance.getHost() + ":" + PORT;
                    log.debug("Adding Hazelcast (prod) cluster member {}", clusterMember);
                    config.getNetworkConfig().getJoin().getTcpIpConfig().addMember(clusterMember);
                }
            }
        }
        config.getMapConfigs().put("default", initializeDefaultMapConfig(properties));
        // Full reference is available at: http://docs.hazelcast.org/docs/management-center/3.9/manual/html/Deploying_and_Starting.html
        config.setManagementCenterConfig(initializeDefaultManagementCenterConfig(properties));
        config.getMapConfigs().put("com.example.demo.domain.*", initializeDomainMapConfig(properties));
        return Hazelcast.newHazelcastInstance(config);
    }

    private ManagementCenterConfig initializeDefaultManagementCenterConfig(AppProperties properties) {
        ManagementCenterConfig managementCenterConfig = new ManagementCenterConfig();
        managementCenterConfig.setUrl(properties.getCache().getManagementCenter().getUrl());
        managementCenterConfig.setEnabled(properties.getCache().getManagementCenter().isEnabled());
        managementCenterConfig.setUpdateInterval(properties.getCache().getManagementCenter().getUpdateInterval());
        return managementCenterConfig;
    }

    private MapConfig initializeDefaultMapConfig(AppProperties properties) {
        MapConfig mapConfig = new MapConfig();

        /*
        Number of backups. If 1 is set as the backup-count for example,
        then all entries of the map will be copied to another JVM for
        fail-safety. Valid numbers are 0 (no backup), 1, 2, 3.
        */
        mapConfig.setBackupCount(properties.getCache().getBackupCount());

        /*
        Valid values are:
        NONE (no eviction),
        LRU (Least Recently Used),
        LFU (Least Frequently Used).
        NONE is the default.
        */
        mapConfig.setEvictionPolicy(EvictionPolicy.LRU);

        /*
        Maximum size of the map. When max size is reached,
        map is evicted based on the policy defined.
        Any integer between 0 and Integer.MAX_VALUE. 0 means
        Integer.MAX_VALUE. Default is 0.
        */
        mapConfig.setMaxSizeConfig(new MaxSizeConfig(0, MaxSizeConfig.MaxSizePolicy.USED_HEAP_SIZE));

        return mapConfig;
    }

    private MapConfig initializeDomainMapConfig(AppProperties properties) {
        MapConfig mapConfig = new MapConfig();
        mapConfig.setTimeToLiveSeconds(properties.getCache().getTimeToLiveSeconds());
        return mapConfig;
    }

}
