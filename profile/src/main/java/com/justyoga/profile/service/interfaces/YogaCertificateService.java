package com.justyoga.profile.service.interfaces;

import com.justyoga.profile.domain.model.mysql.YogaCertificate;
import java.util.List;
import java.util.UUID;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface YogaCertificateService {
    YogaCertificate findById(UUID id);

    @Transactional(propagation = Propagation.REQUIRED)
    YogaCertificate save(YogaCertificate dto);

    @Transactional(propagation = Propagation.REQUIRED)
    List<YogaCertificate> saveAll(List<YogaCertificate> dtoList);

    @Transactional(propagation = Propagation.REQUIRED)
    boolean delete(UUID id);

    YogaCertificate findByName(String name);

    List<YogaCertificate> findAllByNames(List<String> names);

    List<YogaCertificate> findAll();
}
