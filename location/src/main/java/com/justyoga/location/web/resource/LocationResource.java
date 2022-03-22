package com.justyoga.location.web.resource;

import com.justyoga.location.service.interfaces.LocationService;
import com.justyoga.location.web.dto.LocationDTO;
import com.justyoga.location.web.response.AutocompleteResponse;
import com.justyoga.util.response.BaseResponse;
import com.justyoga.util.response.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RefreshScope
public class LocationResource {

    private final LocationService locationService;

    @Value("${fit.yoga}")
    private String config;

    @Autowired
    public LocationResource(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping(value = "/test")
    @ResponseBody
    public ResponseEntity<String> test() {
        return new ResponseEntity<>(config, HttpStatus.OK);
    }

    @GetMapping(value = "/reverse-geocode")
    @ResponseBody
    public ResponseEntity<BaseResponse<LocationDTO>> reverseGeocode(
            @RequestParam(value = "latitude") double latitude,
            @RequestParam(value = "longitude") double longitude) {
        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, locationService.deduceLocation(latitude, longitude)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/autocomplete-results")
    @ResponseBody
    public ResponseEntity<BaseResponse<AutocompleteResponse>> getAutocompleteResults(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "sessionId") String sessionId) {
        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, locationService.getAutocompleteResults(query, sessionId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/place-details")
    @ResponseBody
    public ResponseEntity<BaseResponse<LocationDTO>> getPlaceDetailsFromPlaceId(
            @RequestParam(value = "placeId") String placeId,
            @RequestParam(value = "sessionId") String sessionId) {
        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS,
                        locationService.getPlaceDetailsFromPlaceId(placeId, sessionId)),
                HttpStatus.OK);
    }
}
