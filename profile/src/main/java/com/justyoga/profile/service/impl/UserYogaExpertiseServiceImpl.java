package com.justyoga.profile.service.impl;

import com.justyoga.profile.domain.dao.UserYogaExpertiseRepository;
import com.justyoga.profile.domain.model.mysql.UserYogaExpertise;
import com.justyoga.profile.service.interfaces.UserYogaExpertiseService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserYogaExpertiseServiceImpl implements UserYogaExpertiseService {

    private final UserYogaExpertiseRepository repository;

    @Autowired
    public UserYogaExpertiseServiceImpl(UserYogaExpertiseRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserYogaExpertise> findByUserId(UUID trainerId) {
        return repository.findAllByUserId(trainerId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public UserYogaExpertise save(UserYogaExpertise entity) {
        return repository.save(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean delete(UUID id) {
        Optional<UserYogaExpertise> trainer = repository.findById(id);
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
    public UserYogaExpertise findById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<UserYogaExpertise> getAll() {
        return repository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<UserYogaExpertise> saveAll(List<UserYogaExpertise> entityList) {
        return repository.saveAll(entityList);
    }

    @Override
    public UserYogaExpertise findByUserIdAndYogaExpertiseId(UUID trainerId, UUID yogaExpertiseId) {
        return repository.findByUserIdAndYogaExpertiseId(trainerId, yogaExpertiseId);
    }
}
