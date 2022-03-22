package com.justyoga.place.web.service.interfaces;

import com.justyoga.util.dto.place.*;
import java.util.List;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

public interface PlaceService {

    PlaceIdsDTO getLocationFromIp(String ip);

    List<CountryDTO> getCountries();

    @Transactional
    List<LocalityDTO> getLocality();

    @Transactional
    List<SubLocalityLevel1DTO> getSubLocalityLevel1();

    @Transactional
    List<AdministrativeAreaLevel1DTO> getAdministrativeArea();

    @Transactional
    List<SubLocalityLevel2DTO> getSubLocalityLevel2();

    List<LocalityDTO> getLocalitiesByCountryId(UUID id);

    List<SubLocalityLevel1DTO> getSubLocalityLevel1ByLocalityId(UUID localityId);

    CountryDTO getCountryByName(String name);

    CountryDTO saveCountry(CountryDTO country);

    LocalityDTO getLocalityByNameAndAdministrativeAreaLevel1Id(
            String name, UUID administrativeAreaLevel1Id);

    LocalityDTO saveLocality(LocalityDTO locality);

    AdministrativeAreaLevel1DTO getAdministrativeAreaLevel1ByNameAndCountryId(
            String name, UUID countryId);

    AdministrativeAreaLevel1DTO saveAdministrativeAreaLevel1(
            AdministrativeAreaLevel1DTO administrativeAreaLevel1);

    SubLocalityLevel1DTO getSubLocalityLevel1ByNameAndLocalityId(String name, UUID localityId);

    SubLocalityLevel1DTO saveSubLocalityLevel1(SubLocalityLevel1DTO subLocalityLevel1);

    SubLocalityLevel2DTO getSubLocalityLevel2ByNameAndSubLocalityLevel1Id(
            String name, UUID subLocalityLevel1Id);

    SubLocalityLevel2DTO saveSubLocalityLevel2(SubLocalityLevel2DTO subLocalityLevel2);

    LocationDetailedDTO getLocationDetailedDTO(LocationDTO locationDTO);
}
