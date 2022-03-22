package com.justyoga.profile.service.impl;

import com.justyoga.profile.domain.dao.UserMedicalExpertiseRepository;
import com.justyoga.profile.domain.model.mysql.UserMedicalExpertise;
import com.justyoga.profile.service.interfaces.UserMedicalExpertiseService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserMedicalExpertiseServiceImpl implements UserMedicalExpertiseService {

    private final UserMedicalExpertiseRepository repository;

    @Autowired
    public UserMedicalExpertiseServiceImpl(UserMedicalExpertiseRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserMedicalExpertise> findByUserId(UUID trainerId) {
        return repository.findAllByUserId(trainerId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public UserMedicalExpertise save(UserMedicalExpertise entity) {
        return repository.save(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean delete(UUID id) {
        Optional<UserMedicalExpertise> trainer = repository.findById(id);
        if (trainer.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteAllByUserId(UUID id) {
        repository.deleteAllByUserId(id);
    }

    @Override
    public UserMedicalExpertise findById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public UserMedicalExpertise findByUserIdAndMedicalExpertiseId(
            UUID trainerId, UUID medicalExpertiseId) {
        return repository.findByUserIdAndMedicalExpertiseId(trainerId, medicalExpertiseId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<UserMedicalExpertise> getAll() {
        return repository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<UserMedicalExpertise> saveAll(List<UserMedicalExpertise> entityList) {
        return repository.saveAll(entityList);
    }
}
