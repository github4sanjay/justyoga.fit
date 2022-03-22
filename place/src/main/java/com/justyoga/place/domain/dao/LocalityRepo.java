package com.justyoga.place.domain.dao;

import com.justyoga.place.domain.model.mysql.Locality;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LocalityRepo extends JpaRepository<Locality, UUID> {

    @Query("select u from Locality u")
    Stream<Locality> streamAllLocality();

    Stream<Locality> streamAllByAdministrativeAreaLevel1IdIn(Collection<UUID> ids);

    Locality findByNameAndAdministrativeAreaLevel1Id(String name, UUID administrativeAreaLevel1Id);

    List<Locality> findAllByAdministrativeAreaLevel1Id(UUID administrativeAreaLevel1PublicId);
}
