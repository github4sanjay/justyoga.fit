package com.justyoga.user.web.service.interfaces;

import com.justyoga.util.dto.user.UserDTO;
import com.justyoga.util.page.PageDTO;
import java.util.List;
import java.util.UUID;

public interface ProfileService {

    UserDTO updateProfilePic(UUID userId, String profilePic, UUID currentUUID);

    UserDTO updateCoverPic(UUID userId, String coverPicUrl, UUID currentUUID);

    UserDTO getUserById(UUID id);

    UserDTO getByFirebaseUid(String firebaseUid);

    UserDTO createUser(UserDTO userDTO);

    UserDTO createOrUpdate(UserDTO userDTO);

    PageDTO<UserDTO> findAll(List<UUID> id, Integer page, Integer count, String sort);

    UserDTO createAdmin(UUID userId);
}
