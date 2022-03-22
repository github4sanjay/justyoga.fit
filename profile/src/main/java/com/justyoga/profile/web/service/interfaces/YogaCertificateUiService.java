package com.justyoga.profile.web.service.interfaces;

import com.justyoga.util.dto.profile.UserYogaCertificateDTO;
import com.justyoga.util.dto.profile.YogaCertificateDTO;
import java.util.List;
import java.util.UUID;

public interface YogaCertificateUiService {

    YogaCertificateDTO saveYogaCertificate(YogaCertificateDTO dto);

    List<YogaCertificateDTO> saveAllYogaCertificate(List<YogaCertificateDTO> dtoList);

    List<YogaCertificateDTO> getAllYogaCertificate();

    boolean deleteYogaCertificate(UUID id);

    UserYogaCertificateDTO saveUserYogaCertificate(UserYogaCertificateDTO dto, UUID userID);

    List<UserYogaCertificateDTO> findUserYogaCertificateByTrainerId(UUID trainerId);

    boolean deleteUserYogaCertificate(UUID id, UUID userID);
}
