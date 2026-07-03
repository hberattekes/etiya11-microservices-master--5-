package com.etiya.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;

import java.time.Duration;

@Configuration
public class RedisCacheConfig {

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        // Not: koleksiyonlar (java.util) dışında BigDecimal gibi final java tipleri de
        // JSON'a tip kimliğiyle yazılıyor; okurken validator izin vermezse
        // InvalidTypeIdException fırlar. DTO'lara yeni alan tipi eklenirse
        // paketi burada da izinli olmalı.
        GenericJacksonJsonRedisSerializer serializer = GenericJacksonJsonRedisSerializer.builder()
                .enableDefaultTyping(BasicPolymorphicTypeValidator.builder()
                        .allowIfSubType("com.etiya.productservice.")
                        .allowIfSubType("java.util.")
                        .allowIfSubType("java.math.")
                        .allowIfSubType("java.time.")
                        .build())
                .build();

        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(serializer));
    }
}
