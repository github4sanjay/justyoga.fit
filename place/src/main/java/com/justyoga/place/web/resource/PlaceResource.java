package com.justyoga.place.web.resource;

import com.justyoga.place.web.service.interfaces.PlaceService;
import com.justyoga.util.annotation.MaintainUserContext;
import com.justyoga.util.dto.place.*;
import com.justyoga.util.response.BaseResponse;
import com.justyoga.util.response.Status;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class PlaceResource {

    private final PlaceService placeService;

    @Autowired
    public PlaceResource(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping(value = "/countries/{countryId}/localities")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<LocalityDTO>>> getLocalitiesByCountryId(
            @PathVariable(value = "countryId") UUID countryId) {
        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, placeService.getLocalitiesByCountryId(countryId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/localities/{localityId}/sub-localities-1")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<SubLocalityLevel1DTO>>> getSubLocalities1ByLocalityId(
            @PathVariable(value = "localityId") UUID localityId) {
        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, placeService.getSubLocalityLevel1ByLocalityId(localityId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/countries")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<CountryDTO>>> getCountries() {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, placeService.getCountries()), HttpStatus.OK);
    }

    @GetMapping(value = "/administrative-areas")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<AdministrativeAreaLevel1DTO>>>
            getAdministrativeAreaLevel1() {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, placeService.getAdministrativeArea()),
                HttpStatus.OK);
    }

    @GetMapping(value = "/localities")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<LocalityDTO>>> getLocality() {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, placeService.getLocality()), HttpStatus.OK);
    }

    @GetMapping(value = "/sub-localities-1")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<SubLocalityLevel1DTO>>> getSubLocalityLevel1() {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, placeService.getSubLocalityLevel1()),
                HttpStatus.OK);
    }

    @GetMapping(value = "/sub-localities-2")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<SubLocalityLevel2DTO>>> getSubLocalityLevel2() {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, placeService.getSubLocalityLevel2()),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PostMapping(value = "/places")
    @ResponseBody
    public ResponseEntity<BaseResponse<LocationDetailedDTO>> save(
            @RequestBody LocationDTO locationDTO,
            @RequestHeader("X-Authorization-UUID") UUID userId) {
        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, placeService.getLocationDetailedDTO(locationDTO)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/ip")
    @ResponseBody
    public ResponseEntity<BaseResponse<PlaceIdsDTO>> getPlaceDetailsFromPlaceId(
            @RequestParam(value = "ip") String ip) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, placeService.getLocationFromIp(ip)),
                HttpStatus.OK);
    }
}
