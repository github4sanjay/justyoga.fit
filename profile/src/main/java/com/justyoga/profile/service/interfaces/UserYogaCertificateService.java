package com.justyoga.profile.service.interfaces;

import com.justyoga.profile.domain.model.mysql.UserYogaCertificate;
import java.util.List;
import java.util.UUID;

public interface UserYogaCertificateService {
    List<UserYogaCertificate> findByUserId(UUID trainerId);

    UserYogaCertificate save(UserYogaCertificate dto);

    boolean delete(UUID id);

    void deleteAllByUserId(UUID id);

    UserYogaCertificate findById(UUID id);

    List<UserYogaCertificate> getAll();

    List<UserYogaCertificate> saveAll(List<UserYogaCertificate> dtoList);

    UserYogaCertificate findByUserIdAndYogaCertificateId(UUID trainerId, UUID yogaCertificateId);
}
