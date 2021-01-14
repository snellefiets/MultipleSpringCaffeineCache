package com.example.cache.y.address;

import org.springframework.stereotype.Service;

@Service
public class AddressService {

    public Address findAddress(String id) {
        return Address.builder().number(id).street(id.concat("Street")).build();
    }

}
