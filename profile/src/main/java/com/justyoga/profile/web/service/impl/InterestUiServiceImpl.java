package com.justyoga.profile.web.service.impl;

import com.justyoga.profile.domain.model.mysql.Interest;
import com.justyoga.profile.service.interfaces.InterestService;
import com.justyoga.profile.web.service.interfaces.InterestUiService;
import com.justyoga.util.dto.profile.InterestDTO;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import com.justyoga.util.page.PageDTO;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class InterestUiServiceImpl implements InterestUiService {

    private final InterestService service;
    private final ModelMapper modelMapper;

    @Autowired
    public InterestUiServiceImpl(InterestService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    public Interest convert(InterestDTO dto) {
        if (dto.getId() != null) {
            var interest = service.findById(dto.getId());
            if (interest != null) {
                interest.setBlogger(dto.getBlogger());
                interest.setLookingForTrainer(dto.getLookingForTrainer());
                interest.setTrainer(dto.getTrainer());
                return interest;
            }
        }
        return modelMapper.map(dto, Interest.class);
    }

    public InterestDTO convert(Interest entity) {
        return modelMapper.map(entity, InterestDTO.class);
    }

    @Override
    public InterestDTO findById(UUID id) {
        var interest = service.findById(id);
        if (interest == null) return null;
        else return convert(interest);
    }

    @Override
    public InterestDTO findByUserId(UUID id) {
        var interest = service.findByUserId(id);
        if (interest == null) return null;
        else return convert(interest);
    }

    @Override
    public InterestDTO saveOrUpdate(InterestDTO dto, UUID userId) {
        if (dto != null && !userId.equals(dto.getUserId())) {
            throw new AppException("User not allowed", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        if (dto.getId() == null) {
            var byUserId = service.findByUserId(dto.getUserId());
            if (byUserId != null)
                throw new AppException("Interest already exist", AppStatusCode.INVALID_REQUEST);
        }

        var save = service.save(convert(dto));
        return convert(save);
    }

    @Override
    public PageDTO<InterestDTO> find(Integer page, Integer count, String sort) {

        if (page == null) page = 0;
        if (count == null) count = 10;
        Page<Interest> interests = service.findAll(page, count, sort);
        return PageDTO.<InterestDTO>builder()
                .content(
                        interests.getContent().stream()
                                .map(this::convert)
                                .collect(Collectors.toList()))
                .first(interests.isFirst())
                .hasContent(interests.hasContent())
                .hasNext(interests.hasNext())
                .hasPrevious(interests.hasPrevious())
                .last(interests.isLast())
                .number(interests.getNumber())
                .numberOfElements(interests.getNumberOfElements())
                .size(interests.getSize())
                .build();
    }
}
