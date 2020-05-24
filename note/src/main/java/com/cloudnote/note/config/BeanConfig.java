package com.cloudnote.note.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackages = "com.cloudnote.note.thread")
public class BeanConfig {
    @LoadBalanced//ribbon组件，开启负载均衡
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
