package com.example.cache.y.address;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class AddressCache {

    final private AddressService addressService;

    public AddressCache(AddressService addressService) {

        this.addressService = addressService;
    }

    @Cacheable(cacheManager = AddressCacheConfig.ADDRESS_CACHE_MANAGER_NAME, value = "address_cache")
    public Address findAddress(String customerId) {
        return addressService.findAddress(customerId);
    }

}
