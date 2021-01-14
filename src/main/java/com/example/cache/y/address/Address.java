package com.example.cache.y.address;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class Address {

    private String street;
    private String number;
}
