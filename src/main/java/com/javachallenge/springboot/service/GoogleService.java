package com.javachallenge.springboot.service;

import com.javachallenge.springboot.model.Shop;

/**
 * Created by USER on 26-01-2017.
 */
public interface GoogleService {

     String[] getLatLongPositions(String address) throws Exception;

    void setLatLongForShop(Shop shop);
}
