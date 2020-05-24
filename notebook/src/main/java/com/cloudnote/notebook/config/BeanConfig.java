package com.cloudnote.notebook.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class BeanConfig {
    @LoadBalanced//ribbon组件，开启负载均衡
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
