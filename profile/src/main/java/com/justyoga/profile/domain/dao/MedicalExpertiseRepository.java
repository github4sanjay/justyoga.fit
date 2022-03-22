package com.justyoga.profile.domain.dao;

import com.justyoga.profile.domain.model.mysql.MedicalExpertise;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalExpertiseRepository extends JpaRepository<MedicalExpertise, UUID> {

    MedicalExpertise findByName(String name);

    List<MedicalExpertise> findAllByNameIn(Collection<String> names);
}
