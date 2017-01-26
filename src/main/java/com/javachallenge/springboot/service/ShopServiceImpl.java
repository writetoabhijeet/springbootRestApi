package com.javachallenge.springboot.service;


import com.javachallenge.springboot.model.CustomerLocation;
import com.javachallenge.springboot.model.Shop;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
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

    private static List<Shop> populateDummyShops() {
        return new ArrayList<Shop>();

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

    @Override
    public List<Shop> getNearestShopsForCustomer(CustomerLocation location) {

        List<Shop> result = null;
        TreeMap<Double, List<Shop>> doubleListTreeMap = new TreeMap<>();

        String lat1 = location.getLatitude().trim();
        String lon1 = location.getLongitude().trim();

        for (Shop shop : shops) {
            String lat2 = shop.getLatitude().trim();
            String lon2 = shop.getLongitude().trim();
            if (!StringUtils.isEmpty(lat1) && !StringUtils.isEmpty(lon1) && !StringUtils.isEmpty(lat2) && !StringUtils.isEmpty(lon2)) {
                double distance = getDistance(Double.valueOf(lat1), Double.valueOf(lon1), Double.valueOf(lat2), Double.valueOf(lon2));

                if (doubleListTreeMap.get(distance) != null) {
                    List<Shop> updtShopList = doubleListTreeMap.get(distance);
                    updtShopList.add(shop);
                    doubleListTreeMap.put(distance, updtShopList);
                } else {
                    List<Shop> shopList = new ArrayList<Shop>();
                    shopList.add(shop);
                    doubleListTreeMap.put(distance, shopList);
                }
            }
        }

        return doubleListTreeMap.isEmpty() ? result : doubleListTreeMap.get(doubleListTreeMap.firstKey());


    }

    private double getDistance(double lat1, double lon1, double lat2,
                               double lon2) {

        double R = 6378.1; // Radius of the earth in km
        double dLat = deg2rad(lat2 - lat1);  // deg2rad below
        double dLon = deg2rad(lon2 - lon1);
        double a =
                Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c; // Distance in km
        return d * 1000;
    }

    private double deg2rad(double d) {

        return d * (Math.PI / 180);
    }

}
