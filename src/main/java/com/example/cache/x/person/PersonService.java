package com.example.cache.x.person;

import org.springframework.stereotype.Service;

@Service
public class PersonService {

    public Person findPerson(String id) {
        return Person.builder().name(id + "Name").lastName(id + "Lastname").build();
    }

}
