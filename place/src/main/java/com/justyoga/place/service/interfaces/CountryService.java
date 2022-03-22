package com.justyoga.place.service.interfaces;

import com.justyoga.place.domain.model.mysql.Country;
import com.justyoga.util.dto.cache.GenericCacheDTO;
import com.justyoga.util.dto.place.CountryDTO;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public interface CountryService {

    CountryDTO getCountryByName(String name);

    CountryDTO save(CountryDTO dto);

    boolean addChildrenToCountryCache(UUID id, UUID administrativeAreaLevel1Id);

    Stream<Country> streamAllCountry();

    List<CountryDTO> getAllCountry();

    GenericCacheDTO<UUID, CountryDTO, UUID, UUID> getCountryCacheById(UUID id);

    CountryDTO getCountryById(UUID id);
}
