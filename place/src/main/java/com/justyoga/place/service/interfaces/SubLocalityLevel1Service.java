package com.justyoga.place.service.interfaces;

import com.justyoga.place.domain.model.mysql.SubLocalityLevel1;
import com.justyoga.util.dto.cache.GenericCacheDTO;
import com.justyoga.util.dto.place.SubLocalityLevel1DTO;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public interface SubLocalityLevel1Service {
    List<SubLocalityLevel1DTO> getAllSubLocalityLevel1();

    SubLocalityLevel1DTO findById(UUID id);

    SubLocalityLevel1DTO getSubLocalityLevel1ByNameAndLocalityId(String name, UUID localityId);

    SubLocalityLevel1DTO save(SubLocalityLevel1DTO subLocalityLevel1);

    boolean addChildrenToSubLocalityLevel1Cache(UUID id, UUID subLocalityLevel1Id);

    Stream<SubLocalityLevel1> streamAllByLocalityIdIn(Collection<UUID> localityPublicIds);

    List<SubLocalityLevel1> getSubLocalityLevel1ByLocalityId(UUID localityId);

    GenericCacheDTO<UUID, SubLocalityLevel1DTO, UUID, UUID> getSubLocalityLevel1DTOCacheById(
            UUID id);
}
