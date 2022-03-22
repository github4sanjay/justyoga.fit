package com.justyoga.profile.service.impl;

import com.justyoga.profile.domain.dao.YogaCertificateRepository;
import com.justyoga.profile.domain.model.mysql.YogaCertificate;
import com.justyoga.profile.service.interfaces.YogaCertificateService;
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
public class YogaCertificateServiceImpl implements YogaCertificateService {

    private final YogaCertificateRepository repository;

    @Autowired
    public YogaCertificateServiceImpl(YogaCertificateRepository repository) {
        this.repository = repository;
    }

    @Override
    public YogaCertificate findById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public YogaCertificate save(YogaCertificate entity) {
        return repository.save(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<YogaCertificate> saveAll(List<YogaCertificate> entityList) {
        return repository.saveAll(entityList);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean delete(UUID id) {
        Optional<YogaCertificate> entityOptional = repository.findById(id);
        if (entityOptional.isPresent()) {
            YogaCertificate entity = entityOptional.get();
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public YogaCertificate findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<YogaCertificate> findAllByNames(List<String> names) {
        return repository.findAllByNameIn(names);
    }

    @Override
    public List<YogaCertificate> findAll() {
        return repository.findAll();
    }
}
