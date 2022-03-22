package com.justyoga.profile.web.service.interfaces;

import com.justyoga.util.dto.profile.UserYogaExpertiseDTO;
import com.justyoga.util.dto.profile.YogaExpertiseDTO;
import java.util.List;
import java.util.UUID;

public interface YogaExpertiseUiService {

    YogaExpertiseDTO saveYogaExpertise(YogaExpertiseDTO dto);

    List<YogaExpertiseDTO> saveAllYogaExpertise(List<YogaExpertiseDTO> dtoList);

    List<YogaExpertiseDTO> getAllYogaExpertise();

    boolean deleteYogaExpertise(UUID id);

    UserYogaExpertiseDTO saveUserYogaExpertise(UserYogaExpertiseDTO dto, UUID userID);

    List<UserYogaExpertiseDTO> findUserYogaExpertiseByTrainerId(UUID trainerId);

    boolean deleteUserYogaExpertise(UUID id, UUID userID);
}
