package com.javachallenge.springboot.controller;

import com.javachallenge.springboot.model.CustomerLocation;
import com.javachallenge.springboot.model.Shop;
import com.javachallenge.springboot.service.GoogleService;
import com.javachallenge.springboot.service.ShopService;
import com.javachallenge.springboot.util.AddShopRequest;
import com.javachallenge.springboot.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Controller class to add ashop info to the application. Also find the nearest shop
 * depending upon the customer location
 */
@RestController
@RequestMapping("/api")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);


	@Autowired
	ShopService shopService;

	@Autowired
	GoogleService googleService;

	/**
	 * This method is used to add shop data to the application
	 *
	 * @param request   Object of type shop ,w hich hold the all shop information
	 * @param ucBuilder
	 * @return HTTP response
	 */
	@RequestMapping(value = "/shop/", method = RequestMethod.POST)
	public ResponseEntity<Void> addShop(@RequestBody AddShopRequest request, UriComponentsBuilder ucBuilder) {
		logger.info("Request landed");
		Shop shop = new Shop(request.getName(),request.getAddressNumber(),request.getAddressPostcode());
        googleService.setLatLongForShop(shop);
        shopService.saveShop(shop);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/shop/{id}").buildAndExpand(shop.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

	}

	/**
	 * This method is used to get all the avaiable shop stored in the application
	 * @return List of shop
	 */
	@RequestMapping(value = "/allshops/", method = RequestMethod.GET)
	public ResponseEntity<List<Shop>> getAllShop() {
		logger.info("Fetching all shops");
		List<Shop> shopList = shopService.findAllShop();
		if (shopList == null || shopList.isEmpty()) {
			logger.error("No shop data available");
			return new ResponseEntity(new CustomErrorType("No shop data available"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Shop>>(shopList, HttpStatus.OK);
	}

	/**
	 * This method used to find the neareset location depending uponthe available shop database.
	 * @param location Object which hold lat lon value of the customer.
	 * @return
	 */
    @RequestMapping(value = "/nearByShop/", method = RequestMethod.POST)
    public ResponseEntity<List<Shop>> getNearByShop(@RequestBody CustomerLocation location) {
        logger.info("Fetching all shops");
        List<Shop> shopList = shopService.getNearestShopsForCustomer(location);
        if (shopList == null || shopList.isEmpty()) {
            logger.error("No shop data available");
            return new ResponseEntity(new CustomErrorType("No shop data available"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Shop>>(shopList, HttpStatus.OK);
    }

}