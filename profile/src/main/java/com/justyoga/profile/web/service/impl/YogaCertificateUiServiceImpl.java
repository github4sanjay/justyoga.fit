package com.justyoga.profile.web.service.impl;

import com.justyoga.profile.domain.model.mysql.UserYogaCertificate;
import com.justyoga.profile.domain.model.mysql.YogaCertificate;
import com.justyoga.profile.service.interfaces.UserYogaCertificateService;
import com.justyoga.profile.service.interfaces.YogaCertificateService;
import com.justyoga.profile.web.service.interfaces.YogaCertificateUiService;
import com.justyoga.util.dto.profile.UserYogaCertificateDTO;
import com.justyoga.util.dto.profile.YogaCertificateDTO;
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
public class YogaCertificateUiServiceImpl implements YogaCertificateUiService {

    private final YogaCertificateService yogaCertificateService;
    private final UserYogaCertificateService userYogaCertificateService;
    private final ModelMapper modelMapper;

    @Autowired
    public YogaCertificateUiServiceImpl(
            YogaCertificateService yogaCertificateService,
            UserYogaCertificateService userYogaCertificateService,
            ModelMapper modelMapper) {
        this.yogaCertificateService = yogaCertificateService;
        this.userYogaCertificateService = userYogaCertificateService;
        this.modelMapper = modelMapper;
    }

    private YogaCertificate convert(YogaCertificateDTO dto) {
        return modelMapper.map(dto, YogaCertificate.class);
    }

    private YogaCertificateDTO convert(YogaCertificate entity) {
        return modelMapper.map(entity, YogaCertificateDTO.class);
    }

    private UserYogaCertificate convert(UserYogaCertificateDTO dto) {
        return modelMapper.map(dto, UserYogaCertificate.class);
    }

    private UserYogaCertificateDTO convert(UserYogaCertificate entity) {
        return modelMapper.map(entity, UserYogaCertificateDTO.class);
    }

    @Override
    public YogaCertificateDTO saveYogaCertificate(YogaCertificateDTO dto) {
        YogaCertificate byName = yogaCertificateService.findByName(dto.getName());
        if (byName != null)
            throw new AppException(
                    "Already yoga certificate exist with this name", AppStatusCode.INVALID_REQUEST);
        return convert(yogaCertificateService.save(convert(dto)));
    }

    @Override
    public List<YogaCertificateDTO> saveAllYogaCertificate(List<YogaCertificateDTO> dtoList) {
        List<String> nameList = new ArrayList<>();
        dtoList.forEach(medicalCertificateDTO -> nameList.add(medicalCertificateDTO.getName()));
        List<YogaCertificate> byName = yogaCertificateService.findAllByNames(nameList);
        if (!byName.isEmpty())
            throw new AppException(
                    "Already yoga certificate exist with this name " + byName.get(0).getName(),
                    AppStatusCode.INVALID_REQUEST);
        return yogaCertificateService
                .saveAll(dtoList.stream().map(this::convert).collect(Collectors.toList()))
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<YogaCertificateDTO> getAllYogaCertificate() {
        return yogaCertificateService.findAll().stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteYogaCertificate(UUID id) {
        if (!yogaCertificateService.delete(id)) {
            throw new AppException(
                    "Yoga certificate does not exist", AppStatusCode.INVALID_REQUEST);
        }
        return true;
    }

    @Override
    public UserYogaCertificateDTO saveUserYogaCertificate(UserYogaCertificateDTO dto, UUID userID) {

        if (!userID.equals(dto.getUserId())) {
            throw new AppException(
                    "Not authorized to perform this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        }

        YogaCertificate medicalCertificateDTO =
                yogaCertificateService.findById(dto.getYogaCertificateId());
        if (medicalCertificateDTO == null)
            throw new AppException(
                    "Medical certificate does not exist with this id",
                    AppStatusCode.INVALID_REQUEST);
        UserYogaCertificate byTrainerIdAndMedicalCertificateId =
                userYogaCertificateService.findByUserIdAndYogaCertificateId(
                        dto.getUserId(), dto.getYogaCertificateId());
        if (byTrainerIdAndMedicalCertificateId != null)
            throw new AppException(
                    "Trainer already have this medical certificate", AppStatusCode.INVALID_REQUEST);
        return convert(userYogaCertificateService.save(convert(dto)));
    }

    @Override
    public List<UserYogaCertificateDTO> findUserYogaCertificateByTrainerId(UUID userId) {
        return userYogaCertificateService.findByUserId(userId).stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteUserYogaCertificate(UUID id, UUID userID) {
        UserYogaCertificate byId = userYogaCertificateService.findById(id);
        if (byId == null) {
            throw new AppException(
                    "Trainer yoga certificate does not exist with this id",
                    AppStatusCode.NOT_FOUND);
        }
        if (!userID.equals(byId.getUserId())) {
            throw new AppException(
                    "Not authorized to perform this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        return userYogaCertificateService.delete(id);
    }
}
