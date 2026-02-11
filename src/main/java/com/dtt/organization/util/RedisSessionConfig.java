package com.dtt.organization.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
public class RedisSessionConfig {

    /**
     * IMPORTANT:
     * Use JDK serialization for Spring Session.
     * This is REQUIRED for:
     * - Spring Security
     * - Redis-backed HTTP sessions
     * - GraalVM native image
     */
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return RedisSerializer.java();
    }
}

