package com.justyoga.profile.domain.dao;

import com.justyoga.profile.domain.model.mysql.YogaCertificate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface YogaCertificateRepository extends JpaRepository<YogaCertificate, UUID> {
    @Query("select u from YogaCertificate u")
    Stream<YogaCertificate> streamAll();

    YogaCertificate findByName(String name);

    List<YogaCertificate> findAllByNameIn(Collection<String> names);
}
