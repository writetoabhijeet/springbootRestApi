package com.javachallenge.springboot.model;


/**
 * Created by USER on 26-01-2017.
 */



public class Shop {
    private long id;

    String name;
    String addressNumber;
    String addressPostcode;

    String latitude;
    String longitude;
    public Shop(){
        id=0;
    }

    public Shop(String name, String addressNumber, String addressPostcode) {
        this.name = name;
        this.addressNumber = addressNumber;
        this.addressPostcode = addressPostcode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getAddressPostcode() {
        return addressPostcode;
    }

    public void setAddressPostcode(String addressPostcode) {
        this.addressPostcode = addressPostcode;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Shop other = (Shop) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Shop [id=" + id + ", name=" + name + ", address.no=" + addressNumber
                + ", address.postcode=" + addressPostcode + "]";
    }

}
