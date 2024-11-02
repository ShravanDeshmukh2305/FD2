package com.example.FD.Aggregator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisConfig {

    @Value ("${spring.redis.host}")
    private String redishost;

    @Value("${spring.redis.port}")
    private int redisPort;
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // Create a LettuceConnectionFactory with specified host and port
        LettuceConnectionFactory factory = new LettuceConnectionFactory(redishost, redisPort); // Match Docker service name
        factory.setShareNativeConnection(false); // Prevent sharing native connection
        return factory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
        return template;
    }
}

