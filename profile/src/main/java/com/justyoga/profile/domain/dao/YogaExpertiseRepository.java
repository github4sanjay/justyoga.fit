package com.justyoga.profile.domain.dao;

import com.justyoga.profile.domain.model.mysql.YogaExpertise;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface YogaExpertiseRepository extends JpaRepository<YogaExpertise, UUID> {
    @Query("select u from YogaExpertise u")
    Stream<YogaExpertise> streamAll();

    YogaExpertise findByName(String name);

    List<YogaExpertise> findAllByNameIn(Collection<String> names);
}
