package com.justyoga.util.dto.place;

import java.io.Serializable;

public class LocationFromIpDTO implements Serializable {

    private String ip;
    private String city;
    private String region;
    private String region_code;
    private String country;
    private String country_name;
    private String continent_code;
    private boolean in_eu;
    private String postal;
    private float latitude;
    private float longitude;
    private String timezone;
    private String utc_offset;
    private String country_calling_code;
    private String currency;
    private String languages;
    private String asn;
    private String org;

    public String getIp() {
        return ip;
    }

    public String getCity() {
        return city;
    }

    public String getRegion() {
        return region;
    }

    public String getRegion_code() {
        return region_code;
    }

    public String getCountry() {
        return country;
    }

    public String getCountry_name() {
        return country_name;
    }

    public String getContinent_code() {
        return continent_code;
    }

    public boolean getIn_eu() {
        return in_eu;
    }

    public String getPostal() {
        return postal;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getUtc_offset() {
        return utc_offset;
    }

    public String getCountry_calling_code() {
        return country_calling_code;
    }

    public String getCurrency() {
        return currency;
    }

    public String getLanguages() {
        return languages;
    }

    public String getAsn() {
        return asn;
    }

    public String getOrg() {
        return org;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setRegion_code(String region_code) {
        this.region_code = region_code;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public void setContinent_code(String continent_code) {
        this.continent_code = continent_code;
    }

    public void setIn_eu(boolean in_eu) {
        this.in_eu = in_eu;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public void setUtc_offset(String utc_offset) {
        this.utc_offset = utc_offset;
    }

    public void setCountry_calling_code(String country_calling_code) {
        this.country_calling_code = country_calling_code;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public void setAsn(String asn) {
        this.asn = asn;
    }

    public void setOrg(String org) {
        this.org = org;
    }
}
