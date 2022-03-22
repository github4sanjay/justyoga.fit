package com.justyoga.profile.service.impl;

import com.justyoga.profile.domain.dao.YogaExpertiseRepository;
import com.justyoga.profile.domain.model.mysql.YogaExpertise;
import com.justyoga.profile.service.interfaces.YogaExpertiseService;
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
public class YogaExpertiseServiceImpl implements YogaExpertiseService {

    private final YogaExpertiseRepository repository;

    @Autowired
    public YogaExpertiseServiceImpl(YogaExpertiseRepository repository) {
        this.repository = repository;
    }

    @Override
    public YogaExpertise findById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public YogaExpertise save(YogaExpertise entity) {
        return repository.save(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<YogaExpertise> saveAll(List<YogaExpertise> entityList) {
        return repository.saveAll(entityList);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean delete(UUID id) {
        Optional<YogaExpertise> entityOptional = repository.findById(id);
        if (entityOptional.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public YogaExpertise findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<YogaExpertise> findAllByNames(List<String> names) {
        return repository.findAllByNameIn(names);
    }

    @Override
    public List<YogaExpertise> findAll() {
        return repository.findAll();
    }
}
