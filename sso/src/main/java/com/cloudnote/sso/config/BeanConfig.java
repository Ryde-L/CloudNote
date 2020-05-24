package com.cloudnote.sso.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.JedisPool;

@Configuration
public class BeanConfig {

    @Bean
    public JedisPool jedisPool(){
        return new JedisPool("127.0.0.1", 6379);
    }

    @LoadBalanced//ribbon组件，开启负载均衡
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
