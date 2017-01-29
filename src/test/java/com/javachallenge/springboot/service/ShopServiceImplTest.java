package com.javachallenge.springboot.service;

import com.javachallenge.springboot.model.CustomerLocation;
import com.javachallenge.springboot.model.Shop;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Test class for ShopServiceImpl class
 * Created by USER on 28-01-2017.
 */
public class ShopServiceImplTest {


    private final String PUNE_POSTCODE = "411015";
    private final String MUMBAI_POSTCODE = "400064";
    private final String NAGPUR_POSTCODE = "440021";
    private ShopServiceImpl shopService;

    @Before
    public void setUp() {
        shopService = new ShopServiceImpl();
    }

    @Test
    public void testSaveShop() throws Exception {
        Shop shopObj = buildDummyShopObject();
        shopService.saveShop(shopObj);
        List<Shop> result = shopService.findAllShop();
        Assert.assertNotNull(result);
        Assert.assertEquals("Available number of shops must be 1", 1, result.size());
        Assert.assertEquals("shop post-code must be 411015", "411015", result.get(0).getAddressPostcode());
    }


    @Test
    public void testFindAllShop() throws Exception {
        buildShopDatabase();
        List<Shop> result = shopService.findAllShop();
        Assert.assertNotNull(result);
        Assert.assertEquals("Available number of shops must be 4", 4, result.size());

    }


    @Test
    public void testGetNearestShopsForCustomer() throws Exception {

        //setting customer location to AHEMADNAGAR city.
        CustomerLocation customerLoccation = new CustomerLocation();
        customerLoccation.setLatitude("19.0952075");
        customerLoccation.setLongitude("74.759596");

        //loading all shop info
        buildShopDatabase();

        List<Shop> result = shopService.getNearestShopsForCustomer(customerLoccation);
        Assert.assertNotNull(result);
        Assert.assertEquals("Nearest shop must be at PUNE", PUNE_POSTCODE, result.get(0).getAddressPostcode());


    }

    @After
    public void tearDown() {
        shopService = null;
    }

    private Shop buildDummyShopObject() {
        Shop shopObj = new Shop();
        shopObj.setLongitude("76.12345");
        shopObj.setLatitude("18.98754");
        shopObj.setAddressNumber("2160");
        shopObj.setAddressPostcode(PUNE_POSTCODE);
        shopObj.setName("ABC shop");
        return shopObj;
    }

    private void buildShopDatabase() {
        Shop shopObj1 = buildDummyShopObject();
        shopService.saveShop(shopObj1); // PUNE_POSTCODE
        Shop shopObj2 = buildDummyShopObject();
        shopObj2.setAddressPostcode(MUMBAI_POSTCODE);
        shopService.saveShop(shopObj2);
        Shop shopObj3 = buildDummyShopObject();
        shopObj3.setAddressPostcode(NAGPUR_POSTCODE);
        shopService.saveShop(shopObj3);
    }
}