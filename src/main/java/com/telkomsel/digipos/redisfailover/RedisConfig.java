package com.telkomsel.digipos.redisfailover;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {
    @Bean
    LettuceConnectionFactory redisConnectionFactory(RedisMasterReplicaProperties redisProperties) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisProperties.getMaster().getHost(),
                redisProperties.getMaster().getPort());

        configuration.setPassword(RedisPassword.of(redisProperties.getPassword()));

        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisMasterReplicaProperties redisMasterReplicaProperties) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory(redisMasterReplicaProperties));
        template.setKeySerializer (new StringRedisSerializer());
        template.setHashKeySerializer (new StringRedisSerializer ());
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return template;
    }
    @Bean
    LettuceConnectionFactory redisConnectionFactoryReplica(RedisMasterReplicaProperties redisProperties) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisProperties.getReplica().getHost(),
                redisProperties.getReplica().getPort());

        configuration.setPassword(RedisPassword.of(redisProperties.getPassword()));

        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplateReplica(RedisMasterReplicaProperties redisProperties) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactoryReplica(redisProperties));
        template.setKeySerializer (new StringRedisSerializer());
        template.setHashKeySerializer (new StringRedisSerializer ());
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }
    @Bean
    public CircuitBreaker redisCircuitBreaker(CircuitBreakerRegistry circuitBreakerRegistry) {
        CircuitBreakerConfig cfg = CircuitBreakerConfig.custom()
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .failureRateThreshold(50)
                .minimumNumberOfCalls(3)
                .permittedNumberOfCallsInHalfOpenState(100)
                .slidingWindowSize(10)
                .waitDurationInOpenState(Duration.ofMillis(200))
                .build();

        return circuitBreakerRegistry.circuitBreaker("redisCircuitBreaker", cfg);
    }
}
