package com.justyoga.profile.service.impl;

import com.justyoga.profile.domain.dao.ImageRepository;
import com.justyoga.profile.domain.model.mysql.Image;
import com.justyoga.profile.service.interfaces.ImageService;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository repository;

    @Autowired
    public ImageServiceImpl(ImageRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Image> findByUserId(UUID userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public Page<Image> findAllByUserId(Integer page, Integer count, String sort, UUID userId) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return repository.findAllByUserId(userId, pageable.get());
    }

    @Override
    public Page<Image> findAll(Integer page, Integer count, String sort) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return repository.findAll(pageable.get());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Image save(Image image) {
        return repository.save(image);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean delete(UUID id) {
        Optional<Image> trainer = repository.findById(id);
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
    public Image findById(UUID id) {
        return repository.findById(id).orElse(null);
    }
}
