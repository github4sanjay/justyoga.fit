package com.justyoga.place.web.service.impl;

import com.justyoga.place.service.interfaces.*;
import com.justyoga.place.web.service.interfaces.PlaceService;
import com.justyoga.util.dto.cache.GenericCacheDTO;
import com.justyoga.util.dto.place.*;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PlaceServiceImpl implements PlaceService {

    private final CountryService countryService;
    private final AdministrativeAreaLevel1Service administrativeAreaLevel1Service;
    private final LocalityService localityService;
    private final SubLocalityLevel1Service subLocalityLevel1Service;
    private final SubLocalityLevel2Service subLocalityLevel2Service;

    @Autowired
    public PlaceServiceImpl(
            CountryService countryService,
            AdministrativeAreaLevel1Service administrativeAreaLevel1Service,
            LocalityService localityService,
            SubLocalityLevel1Service subLocalityLevel1Service,
            SubLocalityLevel2Service subLocalityLevel2Service) {
        this.countryService = countryService;
        this.administrativeAreaLevel1Service = administrativeAreaLevel1Service;
        this.localityService = localityService;
        this.subLocalityLevel1Service = subLocalityLevel1Service;
        this.subLocalityLevel2Service = subLocalityLevel2Service;
    }

    @Override
    public PlaceIdsDTO getLocationFromIp(String ip) {
        Mono<LocationFromIpDTO> locationFromIpDTOMono =
                WebClient.create("https://ipapi.co/" + ip + "/json")
                        .get()
                        .retrieve()
                        .onStatus(
                                HttpStatus::is4xxClientError,
                                clientResponse ->
                                        Mono.error(
                                                new AppException(
                                                        "Ip api not working.",
                                                        AppStatusCode.INTERNAL_ERROR)))
                        .onStatus(
                                HttpStatus::is5xxServerError,
                                clientResponse ->
                                        Mono.error(
                                                new AppException(
                                                        "Ip api not working.",
                                                        AppStatusCode.INTERNAL_ERROR)))
                        .bodyToMono(LocationFromIpDTO.class);
        LocationFromIpDTO locationFromIpDTO = locationFromIpDTOMono.block();
        PlaceIdsDTO placeIdsDTO = new PlaceIdsDTO();
        if (locationFromIpDTO == null) return placeIdsDTO;
        if (locationFromIpDTO.getCountry_name() == null) return placeIdsDTO;

        CountryDTO countryByName =
                countryService.getCountryByName(locationFromIpDTO.getCountry_name());
        placeIdsDTO.setCountryId(countryByName == null ? null : countryByName.getId());

        if (locationFromIpDTO.getRegion() == null) return placeIdsDTO;
        AdministrativeAreaLevel1DTO administrativeAreaLevel1DTO =
                administrativeAreaLevel1Service.getAdministrativeAreaLevel1NameAndCountryId(
                        locationFromIpDTO.getRegion(), placeIdsDTO.getCountryId());
        placeIdsDTO.setAdministrativeAreaLevel1Id(
                administrativeAreaLevel1DTO == null ? null : administrativeAreaLevel1DTO.getId());

        if (locationFromIpDTO.getCity() == null) return placeIdsDTO;
        LocalityDTO locality =
                localityService.getLocalityByNameAndAdministrativeAreaLevel1Id(
                        locationFromIpDTO.getCity(), placeIdsDTO.getAdministrativeAreaLevel1Id());
        placeIdsDTO.setLocalityId(locality == null ? null : locality.getId());
        return placeIdsDTO;
    }

    @Transactional
    @Override
    public List<CountryDTO> getCountries() {
        List<CountryDTO> allCountry = countryService.getAllCountry();
        if (allCountry != null) return allCountry;
        return Collections.emptyList();
    }

    @Transactional
    @Override
    public List<AdministrativeAreaLevel1DTO> getAdministrativeArea() {
        List<AdministrativeAreaLevel1DTO> allCountry =
                administrativeAreaLevel1Service.getAllAdministrativeAreaLevel1();
        if (allCountry != null) return allCountry;
        return Collections.emptyList();
    }

    @Transactional
    @Override
    public List<LocalityDTO> getLocality() {
        List<LocalityDTO> allCountry = localityService.getAllLocality();
        if (allCountry != null) return allCountry;
        return Collections.emptyList();
    }

    @Transactional
    @Override
    public List<SubLocalityLevel1DTO> getSubLocalityLevel1() {
        List<SubLocalityLevel1DTO> allCountry = subLocalityLevel1Service.getAllSubLocalityLevel1();
        if (allCountry != null) return allCountry;
        return Collections.emptyList();
    }

    @Transactional
    @Override
    public List<SubLocalityLevel2DTO> getSubLocalityLevel2() {
        List<SubLocalityLevel2DTO> allCountry = subLocalityLevel2Service.getAllSubLocalityLevel2();
        if (allCountry != null) return allCountry;
        return Collections.emptyList();
    }

    @Transactional
    @Override
    public List<LocalityDTO> getLocalitiesByCountryId(UUID id) {
        List<LocalityDTO> idAndNameDTOS = new ArrayList<>();
        if (id == null) {
            throw new AppException("Invalid country ID.", AppStatusCode.INVALID_REQUEST);
        }
        GenericCacheDTO<UUID, CountryDTO, UUID, UUID> countryCacheDTO =
                countryService.getCountryCacheById(id);
        if (countryCacheDTO != null) {
            Set<UUID> administrativeAreaIds = countryCacheDTO.getChildren();
            administrativeAreaIds.forEach(
                    publicId -> {
                        GenericCacheDTO<UUID, AdministrativeAreaLevel1DTO, UUID, UUID> aL1CacheDTO =
                                administrativeAreaLevel1Service
                                        .getAdministrativeAreaLevel1DTOCacheById(publicId);
                        aL1CacheDTO
                                .getChildren()
                                .forEach(
                                        localityId -> {
                                            GenericCacheDTO<UUID, LocalityDTO, UUID, UUID>
                                                    localityCacheDTO =
                                                            localityService.getLocalityCacheById(
                                                                    localityId);
                                            idAndNameDTOS.add(localityCacheDTO.getData());
                                        });
                    });
        }
        return idAndNameDTOS;
    }

    @Transactional
    @Override
    public List<SubLocalityLevel1DTO> getSubLocalityLevel1ByLocalityId(UUID localityId) {
        List<SubLocalityLevel1DTO> idAndNameDTOS = new ArrayList<>();
        if (localityId == null) {
            throw new AppException("Invalid locality ID.", AppStatusCode.INVALID_REQUEST);
        }

        GenericCacheDTO<UUID, LocalityDTO, UUID, UUID> localityCacheDTO =
                localityService.getLocalityCacheById(localityId);

        if (localityCacheDTO == null) return idAndNameDTOS;

        Set<UUID> sL1Ids = localityCacheDTO.getChildren();
        if (sL1Ids == null) return idAndNameDTOS;

        sL1Ids.forEach(
                aLong -> {
                    GenericCacheDTO<UUID, SubLocalityLevel1DTO, UUID, UUID> subLocalityCacheDTO =
                            subLocalityLevel1Service.getSubLocalityLevel1DTOCacheById(aLong);
                    idAndNameDTOS.add(subLocalityCacheDTO.getData());
                });

        return idAndNameDTOS;
    }

    @Override
    public CountryDTO getCountryByName(String name) {
        return countryService.getCountryByName(name);
    }

    @Override
    public CountryDTO saveCountry(CountryDTO country) {
        return countryService.save(country);
    }

    @Override
    public AdministrativeAreaLevel1DTO getAdministrativeAreaLevel1ByNameAndCountryId(
            String name, UUID countryId) {
        return administrativeAreaLevel1Service.getAdministrativeAreaLevel1NameAndCountryId(
                name, countryId);
    }

    @Override
    public AdministrativeAreaLevel1DTO saveAdministrativeAreaLevel1(
            AdministrativeAreaLevel1DTO administrativeAreaLevel1) {
        AdministrativeAreaLevel1DTO save =
                administrativeAreaLevel1Service.save(administrativeAreaLevel1);
        countryService.addChildrenToCountryCache(
                save.getCountryId(), administrativeAreaLevel1.getId());
        return save;
    }

    @Override
    public LocalityDTO getLocalityByNameAndAdministrativeAreaLevel1Id(
            String name, UUID administrativeAreaLevel1Id) {
        return localityService.getLocalityByNameAndAdministrativeAreaLevel1Id(
                name, administrativeAreaLevel1Id);
    }

    @Override
    public LocalityDTO saveLocality(LocalityDTO locality) {
        LocalityDTO save = localityService.save(locality);
        administrativeAreaLevel1Service.addChildrenToAdministrativeAreaLevel1Cache(
                save.getAdministrativeAreaLevel1Id(), save.getId());
        return save;
    }

    @Override
    public SubLocalityLevel1DTO getSubLocalityLevel1ByNameAndLocalityId(
            String name, UUID localityId) {
        return subLocalityLevel1Service.getSubLocalityLevel1ByNameAndLocalityId(name, localityId);
    }

    @Override
    public SubLocalityLevel1DTO saveSubLocalityLevel1(SubLocalityLevel1DTO subLocalityLevel1) {
        SubLocalityLevel1DTO save = subLocalityLevel1Service.save(subLocalityLevel1);
        localityService.addChildrenToLocalityCache(save.getLocalityId(), save.getId());
        return save;
    }

    @Override
    public SubLocalityLevel2DTO getSubLocalityLevel2ByNameAndSubLocalityLevel1Id(
            String name, UUID subLocalityLevel1Id) {
        return subLocalityLevel2Service.getSubLocalityLevel2ByNameAndSubLocalityLevel1Id(
                name, subLocalityLevel1Id);
    }

    @Override
    public SubLocalityLevel2DTO saveSubLocalityLevel2(SubLocalityLevel2DTO subLocalityLevel2) {
        SubLocalityLevel2DTO save = subLocalityLevel2Service.save(subLocalityLevel2);
        subLocalityLevel1Service.addChildrenToSubLocalityLevel1Cache(
                save.getSubLocalityLevel1Id(), save.getId());
        return save;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public LocationDetailedDTO getLocationDetailedDTO(LocationDTO locationDTO) {
        LocationDetailedDTO locationDetailedDTO = new LocationDetailedDTO();
        String countryName = locationDTO.getCountry();
        CountryDTO country = this.getCountryByName(countryName);
        if (country == null) {
            CountryDTO newCountry = new CountryDTO(countryName);
            CountryDTO savedCountry = this.saveCountry(newCountry);
            AdministrativeAreaLevel1DTO administrativeAreaLevel1 =
                    new AdministrativeAreaLevel1DTO(
                            locationDTO.getAdministrativeAreaLevel1(), savedCountry.getId());
            AdministrativeAreaLevel1DTO savedAAL1 =
                    this.saveAdministrativeAreaLevel1(administrativeAreaLevel1);
            LocalityDTO locality = new LocalityDTO(locationDTO.getLocality(), savedAAL1.getId());
            LocalityDTO savedLocality = this.saveLocality(locality);
            SubLocalityLevel1DTO subLocalityLevel1 =
                    new SubLocalityLevel1DTO(
                            locationDTO.getSubLocalityLevel1(), savedLocality.getId());
            SubLocalityLevel1DTO savedSubLocalityLevel1 =
                    this.saveSubLocalityLevel1(subLocalityLevel1);
            SubLocalityLevel2DTO subLocalityLevel2 =
                    new SubLocalityLevel2DTO(
                            locationDTO.getSubLocalityLevel2(), savedSubLocalityLevel1.getId());
            SubLocalityLevel2DTO savedSubLocalityLevel2 =
                    this.saveSubLocalityLevel2(subLocalityLevel2);
            locationDetailedDTO.setCountry(savedCountry);
            locationDetailedDTO.setAdministrativeAreaLevel1(savedAAL1);
            locationDetailedDTO.setLocality(savedLocality);
            locationDetailedDTO.setSubLocalityLevel1(savedSubLocalityLevel1);
            locationDetailedDTO.setSubLocalityLevel2(savedSubLocalityLevel2);
        } else {
            AdministrativeAreaLevel1DTO administrativeAreaLevel1 =
                    this.getAdministrativeAreaLevel1ByNameAndCountryId(
                            locationDTO.getAdministrativeAreaLevel1(), country.getId());
            if (administrativeAreaLevel1 == null) {
                AdministrativeAreaLevel1DTO newAdministrativeAreaLevel1 =
                        new AdministrativeAreaLevel1DTO(
                                locationDTO.getAdministrativeAreaLevel1(), country.getId());
                AdministrativeAreaLevel1DTO savedAAL1 =
                        this.saveAdministrativeAreaLevel1(newAdministrativeAreaLevel1);
                LocalityDTO locality =
                        new LocalityDTO(locationDTO.getLocality(), savedAAL1.getId());
                LocalityDTO savedLocality = this.saveLocality(locality);
                SubLocalityLevel1DTO subLocalityLevel1 =
                        new SubLocalityLevel1DTO(
                                locationDTO.getSubLocalityLevel1(), savedLocality.getId());
                SubLocalityLevel1DTO savedSubLocalityLevel1 =
                        this.saveSubLocalityLevel1(subLocalityLevel1);
                SubLocalityLevel2DTO subLocalityLevel2 =
                        new SubLocalityLevel2DTO(
                                locationDTO.getSubLocalityLevel2(), savedSubLocalityLevel1.getId());
                SubLocalityLevel2DTO savedSubLocalityLevel2 =
                        this.saveSubLocalityLevel2(subLocalityLevel2);
                locationDetailedDTO.setCountry(country);
                locationDetailedDTO.setAdministrativeAreaLevel1(savedAAL1);
                locationDetailedDTO.setLocality(savedLocality);
                locationDetailedDTO.setSubLocalityLevel1(savedSubLocalityLevel1);
                locationDetailedDTO.setSubLocalityLevel2(savedSubLocalityLevel2);
            } else {
                LocalityDTO locality =
                        this.getLocalityByNameAndAdministrativeAreaLevel1Id(
                                locationDTO.getLocality(), administrativeAreaLevel1.getId());
                if (locality == null) {
                    String localityName = locationDTO.getLocality();
                    LocalityDTO newLocality =
                            new LocalityDTO(localityName, administrativeAreaLevel1.getId());
                    LocalityDTO savedLocality = this.saveLocality(newLocality);
                    SubLocalityLevel1DTO subLocalityLevel1 =
                            new SubLocalityLevel1DTO(
                                    locationDTO.getSubLocalityLevel1(), savedLocality.getId());
                    SubLocalityLevel1DTO savedSubLocalityLevel1 =
                            this.saveSubLocalityLevel1(subLocalityLevel1);
                    SubLocalityLevel2DTO subLocalityLevel2 =
                            new SubLocalityLevel2DTO(
                                    locationDTO.getSubLocalityLevel2(),
                                    savedSubLocalityLevel1.getId());
                    SubLocalityLevel2DTO savedSubLocalityLevel2 =
                            this.saveSubLocalityLevel2(subLocalityLevel2);
                    locationDetailedDTO.setCountry(country);
                    locationDetailedDTO.setAdministrativeAreaLevel1(administrativeAreaLevel1);
                    locationDetailedDTO.setLocality(savedLocality);
                    locationDetailedDTO.setSubLocalityLevel1(savedSubLocalityLevel1);
                    locationDetailedDTO.setSubLocalityLevel2(savedSubLocalityLevel2);
                } else {
                    SubLocalityLevel1DTO subLocalityLevel1 =
                            this.getSubLocalityLevel1ByNameAndLocalityId(
                                    locationDTO.getSubLocalityLevel1(), locality.getId());
                    if (subLocalityLevel1 == null) {
                        SubLocalityLevel1DTO newSubLocalityLevel1 =
                                new SubLocalityLevel1DTO(
                                        locationDTO.getSubLocalityLevel1(), locality.getId());
                        SubLocalityLevel1DTO savedSubLocalityLevel1 =
                                this.saveSubLocalityLevel1(newSubLocalityLevel1);
                        SubLocalityLevel2DTO subLocalityLevel2 =
                                new SubLocalityLevel2DTO(
                                        locationDTO.getSubLocalityLevel2(),
                                        savedSubLocalityLevel1.getId());
                        SubLocalityLevel2DTO savedSubLocalityLevel2 =
                                this.saveSubLocalityLevel2(subLocalityLevel2);
                        locationDetailedDTO.setCountry(country);
                        locationDetailedDTO.setAdministrativeAreaLevel1(administrativeAreaLevel1);
                        locationDetailedDTO.setLocality(locality);
                        locationDetailedDTO.setSubLocalityLevel1(savedSubLocalityLevel1);
                        locationDetailedDTO.setSubLocalityLevel2(savedSubLocalityLevel2);
                    } else {
                        SubLocalityLevel2DTO subLocalityLevel2 =
                                this.getSubLocalityLevel2ByNameAndSubLocalityLevel1Id(
                                        locationDTO.getSubLocalityLevel2(),
                                        subLocalityLevel1.getId());
                        if (subLocalityLevel2 == null) {
                            SubLocalityLevel2DTO newSubLocalityLevel2 =
                                    new SubLocalityLevel2DTO(
                                            locationDTO.getSubLocalityLevel2(),
                                            subLocalityLevel1.getId());
                            SubLocalityLevel2DTO savedSubLocalityLevel2 =
                                    this.saveSubLocalityLevel2(newSubLocalityLevel2);
                            locationDetailedDTO.setCountry(country);
                            locationDetailedDTO.setAdministrativeAreaLevel1(
                                    administrativeAreaLevel1);
                            locationDetailedDTO.setLocality(locality);
                            locationDetailedDTO.setSubLocalityLevel1(subLocalityLevel1);
                            locationDetailedDTO.setSubLocalityLevel2(savedSubLocalityLevel2);
                        } else {
                            locationDetailedDTO.setCountry(country);
                            locationDetailedDTO.setAdministrativeAreaLevel1(
                                    administrativeAreaLevel1);
                            locationDetailedDTO.setLocality(locality);
                            locationDetailedDTO.setSubLocalityLevel1(subLocalityLevel1);
                            locationDetailedDTO.setSubLocalityLevel2(subLocalityLevel2);
                        }
                    }
                }
            }
        }
        return locationDetailedDTO;
    }
}
