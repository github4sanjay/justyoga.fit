package com.justyoga.place.domain.dao;

import com.justyoga.place.domain.model.mysql.Country;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CountryRepo extends JpaRepository<Country, UUID> {

    @Query("select u from Country u")
    Stream<Country> streamAllCountry();

    Country findByName(String name);
}
