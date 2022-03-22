package com.justyoga.location.service.impl;

import ch.hsr.geohash.GeoHash;
import com.google.maps.errors.ApiException;
import com.justyoga.location.service.interfaces.GoogleService;
import com.justyoga.location.service.interfaces.LocationService;
import com.justyoga.location.web.dto.LocationDTO;
import com.justyoga.location.web.response.AutocompleteResponse;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {

    private final GoogleService googleService;

    @Autowired
    public LocationServiceImpl(GoogleService googleService) {
        this.googleService = googleService;
    }

    @Override
    public LocationDTO deduceLocation(final double latitude, final double longitude) {
        LocationDTO locationDTO = null;
        try {
            locationDTO = googleService.reverseGeocode(latitude, longitude);
            getLocationDTOWithGeoHash(latitude, longitude, locationDTO);

        } catch (Exception e) {
            throw new AppException(
                    "Google service throw exception " + e.getMessage(),
                    AppStatusCode.INVALID_LOCATION);
        }

        locationDTO.setLatitude(latitude);
        locationDTO.setLongitude(longitude);
        return locationDTO;
    }

    @Override
    public LocationDTO reverseGeocode(final double latitude, final double longitude) {
        try {
            return googleService.reverseGeocode(latitude, longitude);
        } catch (Exception e) {
            throw new AppException(
                    "Google api not working for latitude "
                            + latitude
                            + " and longitude "
                            + longitude,
                    AppStatusCode.INVALID_LOCATION_SEARCH);
        }
    }

    @Override
    public void getLocationDTOWithGeoHash(
            double latitude, double longitude, LocationDTO locationDTO) {
        try {
            locationDTO.setGeohash150(
                    GeoHash.geoHashStringWithCharacterPrecision(latitude, longitude, 3));
            locationDTO.setGeohash50(
                    GeoHash.geoHashStringWithCharacterPrecision(latitude, longitude, 4));
            locationDTO.setGeohash5(
                    GeoHash.geoHashStringWithCharacterPrecision(latitude, longitude, 5));
            locationDTO.setGeohash1(
                    GeoHash.geoHashStringWithCharacterPrecision(latitude, longitude, 6));
        } catch (Exception e) {
            throw new AppException(
                    "Geohash service throw exception " + e.getMessage(),
                    AppStatusCode.INVALID_LOCATION);
        }
    }

    @Override
    public AutocompleteResponse getAutocompleteResults(String query, String sessionId) {
        if (query == null || query.isEmpty()) return null;
        try {
            return googleService.getAutocompleteResults(query, sessionId);
        } catch (InterruptedException | ApiException | IOException e) {
            throw new AppException(
                    "Google api not working for query " + query,
                    AppStatusCode.INVALID_LOCATION_SEARCH);
        }
    }

    @Override
    public LocationDTO getPlaceDetailsFromPlaceId(String placeId, String sessionId) {
        if (placeId == null || placeId.isEmpty()) return null;
        try {
            LocationDTO locationDTO = googleService.getPlaceDetailsFromPlaceId(placeId, sessionId);
            getLocationDTOWithGeoHash(
                    locationDTO.getLatitude(), locationDTO.getLongitude(), locationDTO);
            return locationDTO;
        } catch (InterruptedException | ApiException | IOException e) {
            throw new AppException(
                    "Google api not working for placeId " + placeId,
                    AppStatusCode.INVALID_LOCATION);
        }
    }
}
