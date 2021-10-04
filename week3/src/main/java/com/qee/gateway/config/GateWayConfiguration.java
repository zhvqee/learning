package com.qee.gateway.config;

import com.qee.gateway.balance.LoadBalance;
import com.qee.gateway.balance.impls.RandomLoadBalance;
import com.qee.gateway.gateway.GateWay;
import com.qee.gateway.registry.Registry;
import com.qee.gateway.serviceloader.ServiceLoader;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


@EnableConfigurationProperties(GateWayProperties.class)
public class GateWayConfiguration {

    @Bean
    public GateWayConfig getGateWayConfig(GateWayProperties gateWayProperties) {
        return new GateWayConfig(gateWayProperties);
    }

    @Bean
    public GateWay gateWay(GateWayConfig gateWayConfig) {
        return new GateWay(gateWayConfig);
    }

    @Bean
    public LoadBalance loadBalance(GateWayConfig gateWayConfig) {
        String strategy = gateWayConfig.getRouteSelectStrategy();
        LoadBalance loadBalance = ServiceLoader.getService(strategy, LoadBalance.class);
        return loadBalance == null ? new RandomLoadBalance() : loadBalance;
    }


    @Bean
    public Registry registry(GateWayConfig gateWayConfig) {
        String registryName = gateWayConfig.getRegistryName();
        Registry registry = ServiceLoader.getService(registryName, Registry.class);
        registry.init(gateWayConfig);
        return registry;
    }


}
