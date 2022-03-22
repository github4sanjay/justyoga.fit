package com.justyoga.location.service.impl;

import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import com.justyoga.location.config.GoogleApiConfig;
import com.justyoga.location.service.interfaces.GoogleService;
import com.justyoga.location.util.GoogleResponse;
import com.justyoga.location.web.dto.AutocompleteDTO;
import com.justyoga.location.web.dto.LocationDTO;
import com.justyoga.location.web.response.AutocompleteResponse;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class GoogleServiceImpl implements GoogleService {

    @Override
    public Mono<GoogleResponse> convertToLatLong(Double lat, Double lng) {
        return WebClient.create(
                        String.format(
                                GoogleService.GOOGLE_MAPS_API,
                                String.valueOf(lat),
                                String.valueOf(lng)))
                .get()
                .retrieve()
                .onStatus(
                        HttpStatus::is4xxClientError,
                        clientResponse ->
                                Mono.error(
                                        new AppException(
                                                "Google maps api not working.",
                                                AppStatusCode.INTERNAL_ERROR)))
                .onStatus(
                        HttpStatus::is5xxServerError,
                        clientResponse ->
                                Mono.error(
                                        new AppException(
                                                "Google maps api not working.",
                                                AppStatusCode.INTERNAL_ERROR)))
                .bodyToMono(GoogleResponse.class);
    }

    @Override
    public LocationDTO reverseGeocode(final double latitude, final double longitude)
            throws Exception {
        GeoApiContext context = GoogleApiConfig.context();
        final LatLng latlng = new LatLng(latitude, longitude);
        final GeocodingResult[] results = GeocodingApi.reverseGeocode(context, latlng).await();
        if (results != null && results.length > 0) {
            LocationDTO locationFromGeocodingResult = getLocationFromGeocodingResult(results[0]);
            locationFromGeocodingResult.setLatitude(latitude);
            locationFromGeocodingResult.setLongitude(longitude);
            return locationFromGeocodingResult;
        }

        return null;
    }

    @Override
    public LocationDTO getLocationFromGeocodingResult(GeocodingResult result) {
        return getLocationDTO(result.formattedAddress, result.addressComponents);
    }

    private LocationDTO getLocationDTO(
            String formattedAddress, AddressComponent[] addressComponents) {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setFormattedAddress(formattedAddress);
        if (addressComponents != null) {
            for (AddressComponent addressComponent : addressComponents) {
                AddressComponentType[] types = addressComponent.types;
                if (types != null) {
                    for (AddressComponentType addressComponentType : types) {
                        if (addressComponentType == AddressComponentType.SUBLOCALITY_LEVEL_2) {
                            locationDTO.setSubLocalityLevel2(addressComponent.longName);
                        } else if (addressComponentType
                                == AddressComponentType.SUBLOCALITY_LEVEL_1) {
                            locationDTO.setSubLocalityLevel1(addressComponent.longName);
                        } else if (addressComponentType == AddressComponentType.LOCALITY) {
                            locationDTO.setLocality(addressComponent.longName);
                        } else if (addressComponentType
                                == AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_1) {
                            locationDTO.setAdministrativeAreaLevel1(addressComponent.longName);
                        } else if (addressComponentType == AddressComponentType.COUNTRY) {
                            locationDTO.setCountry(addressComponent.longName);
                        } else if (addressComponentType == AddressComponentType.POSTAL_CODE) {
                            locationDTO.setPostalCode(addressComponent.longName);
                        }
                    }
                }
            }
        }
        return locationDTO;
    }

    @Override
    public AutocompleteResponse getAutocompleteResults(String query, String sessionId)
            throws InterruptedException, ApiException, IOException {
        List<AutocompleteDTO> autocompleteDTOS = new ArrayList<>();
        UUID uuid;
        if (sessionId == null || sessionId.isEmpty()) {
            uuid = UUID.randomUUID();
        } else {
            uuid = UUID.fromString(sessionId);
        }
        AutocompleteResponse autocompleteResponse =
                new AutocompleteResponse(autocompleteDTOS, uuid.toString());
        GeoApiContext context = GoogleApiConfig.context();
        PlaceAutocompleteRequest.SessionToken sessionToken =
                new PlaceAutocompleteRequest.SessionToken(uuid);

        PlaceAutocompleteRequest placeAutocompleteRequest1 =
                PlacesApi.placeAutocomplete(context, query, sessionToken);

        PlaceAutocompleteRequest placeAutocompleteRequest2 =
                PlacesApi.placeAutocomplete(context, query, sessionToken);

        AutocompletePrediction[] autocompletePredictionsEstablishment =
                placeAutocompleteRequest1
                        .types(PlaceAutocompleteType.ESTABLISHMENT)
                        .components()
                        .await();
        AutocompletePrediction[] autocompletePredictionsAddress =
                placeAutocompleteRequest2.types(PlaceAutocompleteType.ADDRESS).components().await();
        for (AutocompletePrediction autocompletePrediction : autocompletePredictionsEstablishment) {
            AutocompleteDTO autocompleteDTO =
                    new AutocompleteDTO(
                            autocompletePrediction.description, autocompletePrediction.placeId);
            autocompleteDTOS.add(autocompleteDTO);
        }
        for (AutocompletePrediction autocompletePrediction : autocompletePredictionsAddress) {
            AutocompleteDTO autocompleteDTO =
                    new AutocompleteDTO(
                            autocompletePrediction.description, autocompletePrediction.placeId);
            autocompleteDTOS.add(autocompleteDTO);
        }
        return autocompleteResponse;
    }

    @Override
    public LocationDTO getPlaceDetailsFromPlaceId(String placeId, String sessionId)
            throws InterruptedException, ApiException, IOException {
        GeoApiContext context = GoogleApiConfig.context();
        UUID uuid;
        if (sessionId == null || sessionId.isEmpty()) {
            uuid = UUID.randomUUID();
        } else {
            uuid = UUID.fromString(sessionId);
        }
        PlaceAutocompleteRequest.SessionToken sessionToken =
                new PlaceAutocompleteRequest.SessionToken(uuid);
        PlaceDetailsRequest placeDetailsRequest =
                PlacesApi.placeDetails(context, placeId, sessionToken);
        PlaceDetails placeDetails = placeDetailsRequest.await();
        LocationDTO locationDTO =
                getLocationDTO(placeDetails.formattedAddress, placeDetails.addressComponents);
        locationDTO.setLatitude(
                (placeDetails.geometry != null && placeDetails.geometry.location != null)
                        ? placeDetails.geometry.location.lat
                        : null);
        locationDTO.setLongitude(
                (placeDetails.geometry != null && placeDetails.geometry.location != null)
                        ? placeDetails.geometry.location.lng
                        : null);
        return locationDTO;
    }
}
