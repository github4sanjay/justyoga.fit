package com.justyoga.user.service.interfaces;

import com.justyoga.util.dto.user.UserDTO;
import com.justyoga.util.page.PageDTO;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;

public interface UserService {

    UserDTO createUser(UserDTO init);

    UserDTO getByFirebaseUid(String username);

    @Transactional
    UserDTO createOrUpdate(UserDTO userDTO);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(UUID id);

    boolean isAdmin(UUID id);

    UserDTO updateProfilePic(String url, UUID id);

    UserDTO updateCoverPic(String url, UUID userId);

    PageDTO<UserDTO> findAll(List<UUID> id, Integer page, Integer count, String sort);

    PageDTO<UserDTO> findAll(Integer page, Integer count, String sort);

    UserDTO createAdmin(UUID userId);

    void deleteUserByFirebaseUUID(String firebaseUUID);
}
