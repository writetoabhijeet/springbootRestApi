package com.javachallenge.springboot.service;

import com.javachallenge.springboot.model.Shop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by USER on 26-01-2017.
 */
@Service("googleService")
public class GoogleServiceImpl implements GoogleService {
    public static final Logger logger = LoggerFactory.getLogger(GoogleServiceImpl.class);

    private static final String SPACE=" ";

    /**
     * This method accept the shop object and call the google map api to get the lat long values
     * for the provided pincode object. Imp.Note : pincode is compulsory else service not get
     * the lat lon values for the shop.
     *
     * @param shop : Object of type SHOP
     */
    @Override
    public void setLatLongForShop(Shop shop) {
        try {
            String[] geoPosition = getLatLongPositions(shop.getAddressPostcode().trim());
            shop.setLatitude(geoPosition[0]);
            shop.setLongitude(geoPosition[1]);
        } catch (Exception e) {
            logger.warn("Failed to set lat long for the given shop address : "+shop.getAddressNumber().concat(SPACE).concat(shop.getAddressPostcode()));
        }

    }


    private String[] getLatLongPositions(String address) throws Exception
    {
        int responseCode = 0;
        String api = buildURL(address);
        URL url = new URL(api);
        HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
        httpConnection.connect();
        responseCode = httpConnection.getResponseCode();
        if(responseCode == 200)
        {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();;
            Document document = builder.parse(httpConnection.getInputStream());
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("/GeocodeResponse/status");
            String status = (String)expr.evaluate(document, XPathConstants.STRING);
            if(status.equals("OK"))
            {
                expr = xpath.compile("//geometry/location/lat");
                String latitude = (String)expr.evaluate(document, XPathConstants.STRING);
                expr = xpath.compile("//geometry/location/lng");
                String longitude = (String)expr.evaluate(document, XPathConstants.STRING);
                return new String[] {latitude, longitude};
            }
            else
            {
                throw new Exception("Error from the API - response status: "+status);
            }
        }
        return null;
    }

    private String buildURL(String address) throws UnsupportedEncodingException {
        return "http://maps.googleapis.com/maps/api/geocode/xml?address=" + URLEncoder.encode(address, "UTF-8") + "&sensor=true";
    }

}
