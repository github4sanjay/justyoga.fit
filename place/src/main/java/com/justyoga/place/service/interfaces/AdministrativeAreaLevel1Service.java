package com.justyoga.place.service.interfaces;

import com.justyoga.place.domain.model.mysql.AdministrativeAreaLevel1;
import com.justyoga.util.dto.cache.GenericCacheDTO;
import com.justyoga.util.dto.place.AdministrativeAreaLevel1DTO;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public interface AdministrativeAreaLevel1Service {

    List<AdministrativeAreaLevel1DTO> getAllAdministrativeAreaLevel1();

    AdministrativeAreaLevel1DTO getAdministrativeAreaLevel1NameAndCountryId(
            String name, UUID countryId);

    AdministrativeAreaLevel1DTO save(AdministrativeAreaLevel1DTO country);

    boolean addChildrenToAdministrativeAreaLevel1Cache(UUID id, UUID localityId);

    Stream<AdministrativeAreaLevel1> streamAllByCountryIdIn(List<UUID> countryPublicIds);

    List<AdministrativeAreaLevel1> getAllByCountryId(UUID countryPublicId);

    GenericCacheDTO<UUID, AdministrativeAreaLevel1DTO, UUID, UUID>
            getAdministrativeAreaLevel1DTOCacheById(UUID id);
}
