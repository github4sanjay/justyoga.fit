package com.justyoga.profile.service.interfaces;

import com.justyoga.profile.domain.model.mysql.MedicalExpertise;
import java.util.List;
import java.util.UUID;

public interface MedicalExpertiseService {
    MedicalExpertise findById(UUID id);

    MedicalExpertise save(MedicalExpertise dto);

    List<MedicalExpertise> saveAll(List<MedicalExpertise> dtoList);

    boolean delete(UUID id);

    MedicalExpertise findByName(String name);

    List<MedicalExpertise> findAllByNames(List<String> names);

    List<MedicalExpertise> findAll();
}
