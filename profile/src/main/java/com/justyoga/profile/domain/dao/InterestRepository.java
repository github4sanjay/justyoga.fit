package com.justyoga.profile.domain.dao;

import com.justyoga.profile.domain.model.mysql.Interest;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestRepository extends JpaRepository<Interest, UUID> {
    Interest findByUserId(UUID userId);
}
