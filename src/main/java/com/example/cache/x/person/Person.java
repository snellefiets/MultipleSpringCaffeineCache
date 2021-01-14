package com.example.cache.x.person;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class Person {

    private String name;
    private String lastName;
}
