package com.example.cache.y;

import com.example.cache.y.person.PersonCache;
import com.example.cache.y.person.PersonService;
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

@SpringBootTest(properties = {"cache.personCache.secondsToExpire=2", "cache.personCache.maxSize=4"})
@ExtendWith(SpringExtension.class)
public class PersonCacheIT {

    @Autowired
    PersonCache personCache;

    @Autowired
    @Qualifier("personCacheManager")
    CacheManager cacheManager;

    @MockBean
    PersonService personService;

    Cache cache;


    @BeforeEach
    void setup() {
        cache = cacheManager.getCache("person_cache");
        cache.clear();

    }

    @Test
    void should_autowire_cache() {
        assertThat(personCache).isNotNull();
    }

    @Test
    void should_cache_a_person() {
        personCache.findPerson("01");
        personCache.findPerson("01");
        verify(personService).findPerson("01");
    }


    @SneakyThrows
    @Test
    void should_expire_a_cached_person() {
        personCache.findPerson("1");
        Thread.sleep(3000);
        personCache.findPerson("1");
        verify(personService, times(2)).findPerson("1");
    }

    @SneakyThrows
    @Test
    void should_reach_cache_maximum() {
        final CaffeineCache caffeineCache = (CaffeineCache) cache;

        for (int i = 0; i < 100; i++) {
            personCache.findPerson(String.valueOf(i));
        }

        Thread.sleep(1000);

        assertThat(caffeineCache.getNativeCache().estimatedSize()).isEqualTo(4);
    }


}
