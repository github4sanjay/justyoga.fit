package com.justyoga.location.service.interfaces;

import com.justyoga.location.web.dto.LocationDTO;
import com.justyoga.location.web.response.AutocompleteResponse;

public interface LocationService {

    LocationDTO deduceLocation(double latitude, double longitude);

    void getLocationDTOWithGeoHash(double latitude, double longitude, LocationDTO locationDTO);

    LocationDTO reverseGeocode(double latitude, double longitude);

    AutocompleteResponse getAutocompleteResults(String query, String sessionId);

    LocationDTO getPlaceDetailsFromPlaceId(String placeId, String sessionId);
}
