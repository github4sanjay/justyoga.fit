package com.justyoga.place.domain.dao;

import com.justyoga.place.domain.model.mysql.AdministrativeAreaLevel1;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdministrativeAreaLevel1Repo
        extends JpaRepository<AdministrativeAreaLevel1, UUID> {

    @Query("select u from AdministrativeAreaLevel1 u")
    Stream<AdministrativeAreaLevel1> streamAllAdministrativeAreaLevel1();

    Stream<AdministrativeAreaLevel1> streamAllByCountryIdIn(Collection<UUID> countryIds);

    List<AdministrativeAreaLevel1> findAllById(UUID countryId);

    List<AdministrativeAreaLevel1> findAllByCountryId(UUID countryId);

    AdministrativeAreaLevel1 findByNameAndCountryId(String name, UUID countryId);
}
