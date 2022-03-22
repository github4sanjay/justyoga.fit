package com.justyoga.profile.service.interfaces;

import com.justyoga.profile.domain.model.mysql.Interest;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface InterestService {

    Interest findById(UUID id);

    Interest findByUserId(UUID id);

    Interest save(Interest dto);

    boolean delete(UUID id);

    Page<Interest> findAll(Integer page, Integer count, String sort);
}
