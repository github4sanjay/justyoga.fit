package com.justyoga.profile.service.interfaces;

import com.justyoga.profile.domain.model.mysql.UserYogaExpertise;
import java.util.List;
import java.util.UUID;

public interface UserYogaExpertiseService {
    List<UserYogaExpertise> findByUserId(UUID trainerId);

    UserYogaExpertise save(UserYogaExpertise dto);

    boolean delete(UUID id);

    void deleteAllByUserId(UUID id);

    UserYogaExpertise findById(UUID id);

    List<UserYogaExpertise> getAll();

    List<UserYogaExpertise> saveAll(List<UserYogaExpertise> dtoList);

    UserYogaExpertise findByUserIdAndYogaExpertiseId(UUID trainerId, UUID yogaExpertiseId);
}
