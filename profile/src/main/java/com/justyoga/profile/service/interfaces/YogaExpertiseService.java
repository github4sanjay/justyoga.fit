package com.justyoga.profile.service.interfaces;

import com.justyoga.profile.domain.model.mysql.YogaExpertise;
import java.util.List;
import java.util.UUID;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface YogaExpertiseService {

    YogaExpertise findById(UUID id);

    @Transactional(propagation = Propagation.REQUIRED)
    YogaExpertise save(YogaExpertise dto);

    @Transactional(propagation = Propagation.REQUIRED)
    List<YogaExpertise> saveAll(List<YogaExpertise> dtoList);

    @Transactional(propagation = Propagation.REQUIRED)
    boolean delete(UUID id);

    YogaExpertise findByName(String name);

    List<YogaExpertise> findAllByNames(List<String> names);

    List<YogaExpertise> findAll();
}
