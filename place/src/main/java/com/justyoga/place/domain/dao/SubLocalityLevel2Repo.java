package com.justyoga.place.domain.dao;

import com.justyoga.place.domain.model.mysql.SubLocalityLevel2;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubLocalityLevel2Repo extends JpaRepository<SubLocalityLevel2, UUID> {

    @Query("select u from SubLocalityLevel2 u")
    Stream<SubLocalityLevel2> streamAllSubLocalityLevel2();

    Stream<SubLocalityLevel2> streamAllBySubLocalityLevel1IdIn(Collection<UUID> localityPublicIds);

    SubLocalityLevel2 findByNameAndSubLocalityLevel1Id(String name, UUID subLocalityLevel1Id);

    List<SubLocalityLevel2> findAllBySubLocalityLevel1Id(UUID subLocalityLevel1Id);
}
