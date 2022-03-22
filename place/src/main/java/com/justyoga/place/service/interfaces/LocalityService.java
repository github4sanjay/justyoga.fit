package com.justyoga.place.service.interfaces;

import com.justyoga.place.domain.model.mysql.Locality;
import com.justyoga.util.dto.cache.GenericCacheDTO;
import com.justyoga.util.dto.place.LocalityDTO;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public interface LocalityService {

    List<LocalityDTO> getAllLocality();

    LocalityDTO findById(UUID id);

    LocalityDTO getLocalityByNameAndAdministrativeAreaLevel1Id(
            String name, UUID administrativeAreaLevel1Id);

    LocalityDTO save(LocalityDTO locality);

    boolean addChildrenToLocalityCache(UUID id, UUID subLocalityLevel1Id);

    GenericCacheDTO<UUID, LocalityDTO, UUID, UUID> getLocalityCacheById(UUID id);

    List<Locality> findAllByAdministrativeAreaLevel1Id(UUID administrativeAreaLevel1Id);

    Stream<Locality> streamAllByAdministrativeAreaLevel1IdIn(Collection<UUID> ids);
}
