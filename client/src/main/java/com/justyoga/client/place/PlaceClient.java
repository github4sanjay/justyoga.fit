package com.justyoga.client.place;

import com.justyoga.util.dto.place.*;
import com.justyoga.util.response.BaseResponse;
import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "place")
public interface PlaceClient {

    @GetMapping(value = "/api/v1/countries/{countryId}/localities")
    ResponseEntity<BaseResponse<List<LocalityDTO>>> getLocalitiesByCountryId(
            @PathVariable(value = "countryId") UUID countryId);

    @GetMapping(value = "/api/v1/localities/{localityId}/sub-localities-1")
    ResponseEntity<BaseResponse<List<SubLocalityLevel1DTO>>> getSubLocalities1ByLocalityId(
            @PathVariable(value = "localityId") UUID localityId);

    @GetMapping(value = "/api/v1/countries")
    ResponseEntity<BaseResponse<List<CountryDTO>>> getCountries();

    @PostMapping(value = "/api/v1/places")
    ResponseEntity<BaseResponse<LocationDetailedDTO>> save(
            @RequestBody LocationDTO locationDTO,
            @RequestHeader("X-Authorization-UUID") UUID userId);
}
