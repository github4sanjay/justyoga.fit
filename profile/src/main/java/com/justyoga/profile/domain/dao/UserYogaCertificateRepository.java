package com.justyoga.profile.domain.dao;

import com.justyoga.profile.domain.model.mysql.UserYogaCertificate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserYogaCertificateRepository extends JpaRepository<UserYogaCertificate, UUID> {
    List<UserYogaCertificate> findAllByUserId(UUID id);

    void deleteAllByUserId(UUID id);

    UserYogaCertificate findByUserIdAndYogaCertificateId(UUID trainerId, UUID yogaCertificateId);
}
