package com.justyoga.location.service.interfaces;

import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.justyoga.location.util.GoogleResponse;
import com.justyoga.location.web.dto.LocationDTO;
import com.justyoga.location.web.response.AutocompleteResponse;
import java.io.IOException;
import reactor.core.publisher.Mono;

public interface GoogleService {
    String GOOGLE_MAPS_API =
            "https://maps.googleapis.com/maps/api/geocode/json?latlng=%s,%s"
                    + "&key=AIzaSyCg9KqjmKWZOFws3MUGPdx4uPJgjlndwds";

    Mono<GoogleResponse> convertToLatLong(Double lat, Double lng);

    LocationDTO reverseGeocode(double latitude, double longitude) throws Exception;

    LocationDTO getLocationFromGeocodingResult(GeocodingResult result);

    AutocompleteResponse getAutocompleteResults(String query, String sessionId)
            throws InterruptedException, ApiException, IOException;

    LocationDTO getPlaceDetailsFromPlaceId(String placeId, String sessionId)
            throws InterruptedException, ApiException, IOException;
}
