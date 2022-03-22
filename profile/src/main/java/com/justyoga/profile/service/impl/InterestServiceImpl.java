package com.justyoga.profile.service.impl;

import com.justyoga.profile.domain.dao.InterestRepository;
import com.justyoga.profile.domain.model.mysql.Interest;
import com.justyoga.profile.service.interfaces.InterestService;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class InterestServiceImpl implements InterestService {

    private final InterestRepository repository;

    @Autowired
    public InterestServiceImpl(InterestRepository repository) {
        this.repository = repository;
    }

    @Override
    public Interest findById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Interest findByUserId(UUID id) {
        return repository.findByUserId(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Interest save(Interest interest) {
        return repository.save(interest);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean delete(UUID id) {
        Optional<Interest> trainer = repository.findById(id);
        if (trainer.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Page<Interest> findAll(Integer page, Integer count, String sort) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return repository.findAll(pageable.get());
    }
}
