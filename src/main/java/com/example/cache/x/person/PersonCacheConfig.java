package com.example.cache.x.person;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

@Configuration
public class PersonCacheConfig {

    public static final String PERSON_CACHE_MANAGER_NAME = "personCacheManager";
    final long secondsToExpire;
    final long maxSize;

    public PersonCacheConfig(@Value("${cache.personCache.secondsToExpire}") long secondsToExpire, @Value("${cache.personCache.maxSize}") long maxSize) {
        this.secondsToExpire = secondsToExpire;
        this.maxSize = maxSize;
    }

    private Caffeine caffeineConfig() {
        return Caffeine.newBuilder()
                .expireAfterWrite(secondsToExpire, TimeUnit.SECONDS)
                .maximumSize(maxSize);
    }

    @Bean(PERSON_CACHE_MANAGER_NAME)
    @Primary
    public CacheManager cacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeineConfig());
        return caffeineCacheManager;
    }

}
