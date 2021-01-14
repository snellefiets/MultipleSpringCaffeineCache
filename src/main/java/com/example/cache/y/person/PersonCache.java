package com.example.cache.y.person;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PersonCache {

    final private PersonService personService;

    public PersonCache(PersonService addressService) {
        this.personService = addressService;
    }

    @Cacheable(cacheManager = PersonCacheConfig.PERSON_CACHE_MANAGER_NAME, value = "person_cache")
    public Person findPerson(String personId) {
        return personService.findPerson(personId);
    }

}
