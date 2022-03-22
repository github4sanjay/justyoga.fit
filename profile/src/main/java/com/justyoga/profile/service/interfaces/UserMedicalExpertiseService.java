package com.justyoga.profile.service.interfaces;

import com.justyoga.profile.domain.model.mysql.UserMedicalExpertise;
import java.util.List;
import java.util.UUID;

public interface UserMedicalExpertiseService {
    List<UserMedicalExpertise> findByUserId(UUID trainerId);

    UserMedicalExpertise save(UserMedicalExpertise dto);

    boolean delete(UUID id);

    void deleteAllByUserId(UUID id);

    UserMedicalExpertise findById(UUID id);

    UserMedicalExpertise findByUserIdAndMedicalExpertiseId(UUID trainerId, UUID medicalExpertiseId);

    List<UserMedicalExpertise> getAll();

    List<UserMedicalExpertise> saveAll(List<UserMedicalExpertise> dtoList);
}
