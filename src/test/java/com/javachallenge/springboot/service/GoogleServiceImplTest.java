package com.javachallenge.springboot.service;

import com.javachallenge.springboot.model.Shop;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.HttpURLConnection;

/**
 * Created by USER on 28-01-2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class GoogleServiceImplTest {

    private static final String PUNE_POSTCODE = "411015";
    @Mock
    public HttpURLConnection httpURLConnection;
    private GoogleServiceImpl googleService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        googleService = new GoogleServiceImpl();
    }

    @Test
    public void testSetLatLongForShop() throws Exception {
        Shop shop = buildDummyShopObject();
        Mockito.when(httpURLConnection.getResponseCode()).thenReturn(200);
        googleService.setLatLongForShop(shop);
        Assert.assertNotNull(shop);
        Assert.assertNotNull(shop.getLatitude());
        Assert.assertNotNull(shop.getLongitude());
    }

    private Shop buildDummyShopObject() {
        Shop shopObj = new Shop();
        shopObj.setAddressNumber("2160");
        shopObj.setAddressPostcode(PUNE_POSTCODE);
        shopObj.setName("ABC shop");
        return shopObj;
    }
}