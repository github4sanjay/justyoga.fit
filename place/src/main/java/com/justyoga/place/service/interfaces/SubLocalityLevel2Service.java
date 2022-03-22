package com.justyoga.place.service.interfaces;

import com.justyoga.place.domain.model.mysql.SubLocalityLevel2;
import com.justyoga.util.dto.place.SubLocalityLevel2DTO;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public interface SubLocalityLevel2Service {

    List<SubLocalityLevel2DTO> getAllSubLocalityLevel2();

    SubLocalityLevel2DTO getSubLocalityLevel2ByNameAndSubLocalityLevel1Id(
            String name, UUID subLocalityLevel1Id);

    SubLocalityLevel2DTO save(SubLocalityLevel2DTO subLocalityLevel2);

    List<SubLocalityLevel2> findAllBySubLocalityLevel1Id(UUID subLocalityLevel1Id);

    Stream<SubLocalityLevel2> streamAllBySubLocalityLevel1IdIn(Collection<UUID> localityIds);
}
