package com.justyoga.profile.service.impl;

import com.justyoga.profile.domain.dao.UserYogaCertificateRepository;
import com.justyoga.profile.domain.model.mysql.UserYogaCertificate;
import com.justyoga.profile.service.interfaces.UserYogaCertificateService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserYogaCertificateServiceImpl implements UserYogaCertificateService {

    private final UserYogaCertificateRepository repository;

    @Autowired
    public UserYogaCertificateServiceImpl(UserYogaCertificateRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserYogaCertificate> findByUserId(UUID userId) {
        return repository.findAllByUserId(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public UserYogaCertificate save(UserYogaCertificate entity) {
        return repository.save(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean delete(UUID id) {
        Optional<UserYogaCertificate> trainer = repository.findById(id);
        if (trainer.isPresent()) {
            UserYogaCertificate entity = trainer.get();
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
    public UserYogaCertificate findById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<UserYogaCertificate> getAll() {
        return repository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<UserYogaCertificate> saveAll(List<UserYogaCertificate> entityList) {
        return repository.saveAll(entityList);
    }

    @Override
    public UserYogaCertificate findByUserIdAndYogaCertificateId(
            UUID trainerId, UUID yogaCertificateId) {
        return repository.findByUserIdAndYogaCertificateId(trainerId, yogaCertificateId);
    }
}
