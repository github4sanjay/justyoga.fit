package com.justyoga.profile.domain.dao;

import com.justyoga.profile.domain.model.mysql.UserYogaExpertise;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserYogaExpertiseRepository extends JpaRepository<UserYogaExpertise, UUID> {

    List<UserYogaExpertise> findAllByUserId(UUID id);

    void deleteAllByUserId(UUID id);

    UserYogaExpertise findByUserIdAndYogaExpertiseId(UUID trainerId, UUID yogaExpertiseId);
}
