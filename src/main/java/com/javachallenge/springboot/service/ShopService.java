package com.javachallenge.springboot.service;


import com.javachallenge.springboot.model.CustomerLocation;
import com.javachallenge.springboot.model.Shop;

import java.util.List;

/**
 * Created by Shop on 26-01-2017.
 */
public interface ShopService {

    void saveShop(Shop Shop);

    List<Shop> findAllShop();

    List<Shop> getNearestShopsForCustomer(CustomerLocation location);
}
