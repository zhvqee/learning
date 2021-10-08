package com.test.config;

import com.test.process.RegistryProcessBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {

    @Bean
    public RegistryProcessBeanPostProcessor registryProcessBeanPostProcessor() {
        return new RegistryProcessBeanPostProcessor();
    }
}
