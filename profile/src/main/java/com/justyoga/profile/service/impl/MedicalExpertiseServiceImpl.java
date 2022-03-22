package com.justyoga.profile.service.impl;

import com.justyoga.profile.domain.dao.MedicalExpertiseRepository;
import com.justyoga.profile.domain.model.mysql.MedicalExpertise;
import com.justyoga.profile.service.interfaces.MedicalExpertiseService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class MedicalExpertiseServiceImpl implements MedicalExpertiseService {

    private final MedicalExpertiseRepository repository;

    @Autowired
    public MedicalExpertiseServiceImpl(MedicalExpertiseRepository repository) {
        this.repository = repository;
    }

    @Override
    public MedicalExpertise findById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public MedicalExpertise save(MedicalExpertise entity) {
        return repository.save(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<MedicalExpertise> saveAll(List<MedicalExpertise> entityList) {
        return repository.saveAll(entityList);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean delete(UUID id) {
        Optional<MedicalExpertise> trainer = repository.findById(id);
        if (trainer.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public MedicalExpertise findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<MedicalExpertise> findAllByNames(List<String> names) {
        return repository.findAllByNameIn(names);
    }

    @Override
    public List<MedicalExpertise> findAll() {
        return repository.findAll();
    }
}
