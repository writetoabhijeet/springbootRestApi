package com.javachallenge.springboot.service;


import com.javachallenge.springboot.model.Shop;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by USER on 26-01-2017.
 */
@Service("shopService")
public class ShopServiceImpl implements ShopService {

    private static final AtomicLong counter = new AtomicLong();

    private static List<Shop> shops;

    static{
        shops = populateDummyShops();
    }

    @Override
    public void saveShop(Shop shop) {
        shop.setId(counter.incrementAndGet());
        shops.add(shop);
    }

    @Override
    public List<Shop> findAllShop() {
        return shops;
    }

    private static List<Shop> populateDummyShops(){
        return new ArrayList<Shop>();

    }

}
