package com.justyoga.profile.domain.dao;

import com.justyoga.profile.domain.model.mysql.UserMedicalExpertise;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMedicalExpertiseRepository extends JpaRepository<UserMedicalExpertise, UUID> {

    List<UserMedicalExpertise> findAllByUserId(UUID id);

    UserMedicalExpertise findByUserIdAndMedicalExpertiseId(UUID trainerId, UUID medicalExpertiseId);

    void deleteAllByUserId(UUID id);
}
