package com.justyoga.profile.web.service.impl;

import com.justyoga.profile.domain.model.mysql.MedicalExpertise;
import com.justyoga.profile.domain.model.mysql.UserMedicalExpertise;
import com.justyoga.profile.service.interfaces.MedicalExpertiseService;
import com.justyoga.profile.service.interfaces.UserMedicalExpertiseService;
import com.justyoga.profile.web.service.interfaces.MedicalExpertiseUiService;
import com.justyoga.util.dto.profile.MedicalExpertiseDTO;
import com.justyoga.util.dto.profile.UserMedicalExpertiseDTO;
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
public class MedicalExpertiseUiServiceImpl implements MedicalExpertiseUiService {

    private final MedicalExpertiseService medicalExpertiseService;
    private final UserMedicalExpertiseService userMedicalExpertiseService;
    private final ModelMapper modelMapper;

    @Autowired
    public MedicalExpertiseUiServiceImpl(
            MedicalExpertiseService medicalExpertiseService,
            UserMedicalExpertiseService userMedicalExpertiseService,
            ModelMapper modelMapper) {
        this.medicalExpertiseService = medicalExpertiseService;
        this.userMedicalExpertiseService = userMedicalExpertiseService;
        this.modelMapper = modelMapper;
    }

    private MedicalExpertise convert(MedicalExpertiseDTO dto) {
        return modelMapper.map(dto, MedicalExpertise.class);
    }

    private MedicalExpertiseDTO convert(MedicalExpertise entity) {
        return modelMapper.map(entity, MedicalExpertiseDTO.class);
    }

    private UserMedicalExpertise convert(UserMedicalExpertiseDTO dto) {
        return modelMapper.map(dto, UserMedicalExpertise.class);
    }

    private UserMedicalExpertiseDTO convert(UserMedicalExpertise entity) {
        return modelMapper.map(entity, UserMedicalExpertiseDTO.class);
    }

    @Override
    public MedicalExpertiseDTO saveMedicalExpertise(MedicalExpertiseDTO medicalExpertiseDTO) {
        MedicalExpertise byName = medicalExpertiseService.findByName(medicalExpertiseDTO.getName());
        if (byName != null)
            throw new AppException(
                    "Already medical expertise exist with this name",
                    AppStatusCode.INVALID_REQUEST);
        return convert(medicalExpertiseService.save(convert(medicalExpertiseDTO)));
    }

    @Override
    public List<MedicalExpertiseDTO> saveAllMedicalExpertise(
            List<MedicalExpertiseDTO> medicalExpertiseDTOList) {
        List<String> nameList = new ArrayList<>();
        medicalExpertiseDTOList.forEach(
                medicalExpertiseDTO -> nameList.add(medicalExpertiseDTO.getName()));
        List<MedicalExpertise> byName = medicalExpertiseService.findAllByNames(nameList);
        if (!byName.isEmpty())
            throw new AppException(
                    "Already medical expertise exist with this name " + byName.get(0).getName(),
                    AppStatusCode.INVALID_REQUEST);
        return medicalExpertiseService
                .saveAll(
                        medicalExpertiseDTOList.stream()
                                .map(this::convert)
                                .collect(Collectors.toList()))
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicalExpertiseDTO> getAllMedicalExpertise() {
        return medicalExpertiseService.findAll().stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteMedicalExpertise(UUID id) {
        if (!medicalExpertiseService.delete(id)) {
            throw new AppException("Medical expertise does not exist", AppStatusCode.NOT_FOUND);
        }
        return true;
    }

    @Override
    public UserMedicalExpertiseDTO saveUserMedicalExpertise(
            UserMedicalExpertiseDTO trainerMedicalExpertiseDTO, UUID userID) {

        if (!userID.equals(trainerMedicalExpertiseDTO.getUserId())) {
            throw new AppException(
                    "Not authorized to perform this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        MedicalExpertise medicalExpertiseDTO =
                medicalExpertiseService.findById(
                        trainerMedicalExpertiseDTO.getMedicalExpertiseId());
        if (medicalExpertiseDTO == null)
            throw new AppException(
                    "Medical expertise does not exist with this id", AppStatusCode.INVALID_REQUEST);
        UserMedicalExpertise byTrainerIdAndMedicalExpertiseId =
                userMedicalExpertiseService.findByUserIdAndMedicalExpertiseId(
                        trainerMedicalExpertiseDTO.getUserId(),
                        trainerMedicalExpertiseDTO.getMedicalExpertiseId());
        if (byTrainerIdAndMedicalExpertiseId != null)
            throw new AppException(
                    "Trainer already have this medical expertise", AppStatusCode.INVALID_REQUEST);
        return convert(userMedicalExpertiseService.save(convert(trainerMedicalExpertiseDTO)));
    }

    @Override
    public List<UserMedicalExpertiseDTO> findUserMedicalExpertiseByTrainerId(UUID trainerId) {
        return userMedicalExpertiseService.findByUserId(trainerId).stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteUserMedicalExpertise(UUID id, UUID userID) {
        UserMedicalExpertise byId = userMedicalExpertiseService.findById(id);
        if (byId == null) {
            throw new AppException(
                    "User yoga expertise does not exist with this id", AppStatusCode.NOT_FOUND);
        }
        if (!userID.equals(byId.getUserId())) {
            throw new AppException(
                    "Not authorized to perform this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        return userMedicalExpertiseService.delete(id);
    }
}
