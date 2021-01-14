package com.example.cache.y.address;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class AddressCacheConfig {


    public static final String ADDRESS_CACHE_MANAGER_NAME = "addressCacheManager";
    final long secondsToExpire;
    final long maxSize;

    public AddressCacheConfig(@Value("${cache.addressCache.secondsToExpire}") long secondsToExpire, @Value("${cache.addressCache.maxSize}") long maxSize) {
        this.secondsToExpire = secondsToExpire;
        this.maxSize = maxSize;
    }

    private Caffeine caffeineConfig() {
        return Caffeine.newBuilder()
                .expireAfterWrite(secondsToExpire, TimeUnit.SECONDS)
                .maximumSize(maxSize);
    }

    @Bean(ADDRESS_CACHE_MANAGER_NAME)
    public CacheManager cacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeineConfig());
        return caffeineCacheManager;
    }

}
