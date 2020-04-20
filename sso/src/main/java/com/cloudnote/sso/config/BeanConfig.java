package com.cloudnote.sso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

@Configuration
public class BeanConfig {

    @Bean
    public JedisPool jedisPool(){
        return new JedisPool("127.0.0.1", 6379);
    }
}
