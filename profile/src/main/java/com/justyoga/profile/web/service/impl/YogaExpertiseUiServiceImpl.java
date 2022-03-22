package com.justyoga.profile.web.service.impl;

import com.justyoga.profile.domain.model.mysql.UserYogaExpertise;
import com.justyoga.profile.domain.model.mysql.YogaExpertise;
import com.justyoga.profile.service.interfaces.UserYogaExpertiseService;
import com.justyoga.profile.service.interfaces.YogaExpertiseService;
import com.justyoga.profile.web.service.interfaces.YogaExpertiseUiService;
import com.justyoga.util.dto.profile.UserYogaExpertiseDTO;
import com.justyoga.util.dto.profile.YogaExpertiseDTO;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YogaExpertiseUiServiceImpl implements YogaExpertiseUiService {

    private final YogaExpertiseService yogaExpertiseService;
    private final UserYogaExpertiseService userYogaExpertiseService;
    private final ModelMapper modelMapper;

    @Autowired
    public YogaExpertiseUiServiceImpl(
            YogaExpertiseService yogaExpertiseService,
            UserYogaExpertiseService userYogaExpertiseService,
            ModelMapper modelMapper) {
        this.yogaExpertiseService = yogaExpertiseService;
        this.userYogaExpertiseService = userYogaExpertiseService;
        this.modelMapper = modelMapper;
    }

    private YogaExpertise convert(YogaExpertiseDTO dto) {
        return modelMapper.map(dto, YogaExpertise.class);
    }

    private YogaExpertiseDTO convert(YogaExpertise entity) {
        return modelMapper.map(entity, YogaExpertiseDTO.class);
    }

    private UserYogaExpertise convert(UserYogaExpertiseDTO dto) {
        return modelMapper.map(dto, UserYogaExpertise.class);
    }

    private UserYogaExpertiseDTO convert(UserYogaExpertise entity) {
        return modelMapper.map(entity, UserYogaExpertiseDTO.class);
    }

    @Override
    public YogaExpertiseDTO saveYogaExpertise(YogaExpertiseDTO dto) {
        YogaExpertise byName = yogaExpertiseService.findByName(dto.getName());
        if (byName != null)
            throw new AppException(
                    "Already yoga expertise exist with this name", AppStatusCode.INVALID_REQUEST);
        return convert(yogaExpertiseService.save(convert(dto)));
    }

    @Override
    public List<YogaExpertiseDTO> saveAllYogaExpertise(List<YogaExpertiseDTO> dtoList) {
        List<String> nameList = new ArrayList<>();
        dtoList.forEach(medicalExpertiseDTO -> nameList.add(medicalExpertiseDTO.getName()));
        List<YogaExpertise> byName = yogaExpertiseService.findAllByNames(nameList);
        if (!byName.isEmpty())
            throw new AppException(
                    "Already yoga expertise exist with this name " + byName.get(0).getName(),
                    AppStatusCode.INVALID_REQUEST);
        return yogaExpertiseService
                .saveAll(dtoList.stream().map(this::convert).collect(Collectors.toList()))
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<YogaExpertiseDTO> getAllYogaExpertise() {
        return yogaExpertiseService.findAll().stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteYogaExpertise(UUID id) {
        if (!yogaExpertiseService.delete(id)) {
            throw new AppException("Yoga expertise does not exist", AppStatusCode.NOT_FOUND);
        }
        return true;
    }

    @Override
    public UserYogaExpertiseDTO saveUserYogaExpertise(UserYogaExpertiseDTO dto, UUID userID) {
        if (!userID.equals(dto.getUserId())) {
            throw new AppException(
                    "Not authorized to perform this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        YogaExpertise medicalExpertiseDTO = yogaExpertiseService.findById(dto.getYogaExpertiseId());
        if (medicalExpertiseDTO == null)
            throw new AppException(
                    "Medical expertise does not exist with this id", AppStatusCode.INVALID_REQUEST);
        UserYogaExpertise byTrainerIdAndMedicalExpertiseId =
                userYogaExpertiseService.findByUserIdAndYogaExpertiseId(
                        dto.getUserId(), dto.getYogaExpertiseId());
        if (byTrainerIdAndMedicalExpertiseId != null)
            throw new AppException(
                    "User already have this medical expertise", AppStatusCode.UNAUTHORIZED_ERROR);
        return convert(userYogaExpertiseService.save(convert(dto)));
    }

    @Override
    public List<UserYogaExpertiseDTO> findUserYogaExpertiseByTrainerId(UUID userId) {
        return userYogaExpertiseService.findByUserId(userId).stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteUserYogaExpertise(UUID id, UUID userID) {
        UserYogaExpertise byId = userYogaExpertiseService.findById(id);
        if (byId == null) {
            throw new AppException(
                    "User yoga expertise does not exist with this id", AppStatusCode.NOT_FOUND);
        }
        if (!userID.equals(byId.getUserId())) {
            throw new AppException(
                    "Not authorized to perform this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        return userYogaExpertiseService.delete(id);
    }
}
