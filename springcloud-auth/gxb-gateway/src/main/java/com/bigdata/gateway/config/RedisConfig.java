package com.bigdata.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.io.Serializable;

/**
 * Redis配置类
 *
 * @author fwj
 * @date 2019-02-15 10:53
 **/
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfig {
    private String host;
    private Integer port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * 获取LettuceConnectionFactory工厂
     * @return RedisConnectionFactory
     */
    @Bean
    public RedisConnectionFactory getRedisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(host, port));
    }

    @Bean
    public RedisTemplate<String, Serializable> getRedisTemplate() {
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(getRedisConnectionFactory());
        setMySerializer(redisTemplate);
        return  redisTemplate;
    }

    /**
     * 设置序列化方法
     * @param redisTemplate
     */
    private void setMySerializer(RedisTemplate<String, Serializable> redisTemplate) {
        JdkSerializationRedisSerializer redisSerializer = new JdkSerializationRedisSerializer();
        redisTemplate.setKeySerializer(redisTemplate.getStringSerializer());
        redisTemplate.setValueSerializer(redisSerializer);
    }
}
