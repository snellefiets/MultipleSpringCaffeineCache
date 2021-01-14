package com.example.cache.x;

import com.example.cache.x.address.AddressCache;
import com.example.cache.x.address.AddressService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(properties = {"cache.addressCache.secondsToExpire=2", "cache.addressCache.maxSize=2"})
@ExtendWith(SpringExtension.class)
public class AddressCacheIT {

    @Autowired
    AddressCache addressCache;

    @Autowired
    @Qualifier("addressCacheManager")
    CacheManager cacheManager;

    @MockBean
    AddressService addressService;

    Cache cache;


    @BeforeEach
    void setup() {
        cache = cacheManager.getCache("address_cache");
        cache.clear();

    }

    @Test
    void should_autowire_cache() {
        assertThat(addressCache).isNotNull();
    }

    @Test
    void should_cache_an_address() {
        addressCache.findAddress("01");
        addressCache.findAddress("01");
        verify(addressService).findAddress("01");
    }


    @SneakyThrows
    @Test
    void should_expire_a_cached_address() {
        addressCache.findAddress("1");
        Thread.sleep(3000);
        addressCache.findAddress("1");
        verify(addressService, times(2)).findAddress("1");
    }

    @SneakyThrows
    @Test
    void should_reach_cache_maximum() {
        final CaffeineCache caffeineCache = (CaffeineCache) cache;

        for (int i = 0; i < 100; i++) {
            addressCache.findAddress(String.valueOf(i));
        }

        Thread.sleep(1000);

        assertThat(caffeineCache.getNativeCache().estimatedSize()).isEqualTo(2);
    }


}
