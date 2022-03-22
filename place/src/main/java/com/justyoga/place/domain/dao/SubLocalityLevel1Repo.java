package com.justyoga.place.domain.dao;

import com.justyoga.place.domain.model.mysql.SubLocalityLevel1;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubLocalityLevel1Repo extends JpaRepository<SubLocalityLevel1, UUID> {

    @Query("select u from SubLocalityLevel1 u")
    Stream<SubLocalityLevel1> streamAllSubLocalityLevel1();

    Stream<SubLocalityLevel1> streamAllByLocalityId(UUID localityId);

    Stream<SubLocalityLevel1> streamAllByLocalityIdIn(Collection<UUID> localityIds);

    SubLocalityLevel1 findByNameAndLocalityId(String name, UUID localityId);

    List<SubLocalityLevel1> findByLocalityId(UUID localityId);
}
