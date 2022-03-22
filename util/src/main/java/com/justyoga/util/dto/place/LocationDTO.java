package com.justyoga.util.dto.place;

import java.io.Serializable;

public class LocationDTO implements Serializable {

    private String administrativeAreaLevel1;
    private String country;
    private String formattedAddress;
    private String geohash1;
    private String geohash5;
    private String geohash50;
    private String geohash150;
    private Double latitude;
    private Double longitude;
    private String postalCode;
    private String locality;
    private String subLocalityLevel1;
    private String subLocalityLevel2;

    public String getAdministrativeAreaLevel1() {
        return administrativeAreaLevel1;
    }

    public void setAdministrativeAreaLevel1(String administrativeAreaLevel1) {
        this.administrativeAreaLevel1 = administrativeAreaLevel1;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getGeohash1() {
        return geohash1;
    }

    public void setGeohash1(String geohash1) {
        this.geohash1 = geohash1;
    }

    public String getGeohash5() {
        return geohash5;
    }

    public void setGeohash5(String geohash5) {
        this.geohash5 = geohash5;
    }

    public String getGeohash50() {
        return geohash50;
    }

    public void setGeohash50(String geohash50) {
        this.geohash50 = geohash50;
    }

    public String getGeohash150() {
        return geohash150;
    }

    public void setGeohash150(String geohash150) {
        this.geohash150 = geohash150;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getSubLocalityLevel1() {
        return subLocalityLevel1;
    }

    public void setSubLocalityLevel1(String subLocalityLevel1) {
        this.subLocalityLevel1 = subLocalityLevel1;
    }

    public String getSubLocalityLevel2() {
        return subLocalityLevel2;
    }

    public void setSubLocalityLevel2(String subLocalityLevel2) {
        this.subLocalityLevel2 = subLocalityLevel2;
    }
}
