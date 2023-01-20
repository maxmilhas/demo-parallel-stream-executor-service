package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorServiceConfig {

    @Bean
    public ExecutorService customExecutorService() {
        return Executors.newFixedThreadPool(100, new CustomizableThreadFactory("ProviderFixedThreadPool-"));
        //return Executors.newCachedThreadPool(new CustomizableThreadFactory("ProviderCachedThreadPool-"));
        //return Executors.newVirtualThreadPerTaskExecutor();
    }
}
