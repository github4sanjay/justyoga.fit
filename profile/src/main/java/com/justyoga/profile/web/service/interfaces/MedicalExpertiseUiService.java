package com.justyoga.profile.web.service.interfaces;

import com.justyoga.util.dto.profile.MedicalExpertiseDTO;
import com.justyoga.util.dto.profile.UserMedicalExpertiseDTO;
import java.util.List;
import java.util.UUID;

public interface MedicalExpertiseUiService {
    MedicalExpertiseDTO saveMedicalExpertise(MedicalExpertiseDTO medicalExpertiseDTO);

    List<MedicalExpertiseDTO> saveAllMedicalExpertise(
            List<MedicalExpertiseDTO> medicalExpertiseDTOList);

    List<MedicalExpertiseDTO> getAllMedicalExpertise();

    boolean deleteMedicalExpertise(UUID id);

    UserMedicalExpertiseDTO saveUserMedicalExpertise(
            UserMedicalExpertiseDTO trainerMedicalExpertiseDTO, UUID userID);

    List<UserMedicalExpertiseDTO> findUserMedicalExpertiseByTrainerId(UUID trainerId);

    boolean deleteUserMedicalExpertise(UUID id, UUID userID);
}
