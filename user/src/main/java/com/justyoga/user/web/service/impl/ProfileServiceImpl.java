package com.justyoga.user.web.service.impl;

import com.justyoga.client.library.MediaClient;
import com.justyoga.user.cache.service.BucketVsUrl;
import com.justyoga.user.service.impl.UserServiceImpl;
import com.justyoga.user.service.interfaces.UserService;
import com.justyoga.user.web.service.interfaces.ProfileService;
import com.justyoga.util.dto.user.UserDTO;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import com.justyoga.util.page.PageDTO;
import com.justyoga.util.response.BaseResponse;
import com.justyoga.util.response.Status;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private final UserService userService;
    private final MediaClient mediaClient;
    private final BucketVsUrl bucketVsUrl;

    @Autowired
    public ProfileServiceImpl(
            @Qualifier(UserServiceImpl.NAME) UserService userService,
            MediaClient mediaClient,
            BucketVsUrl bucketVsUrl) {
        this.userService = userService;
        this.mediaClient = mediaClient;
        this.bucketVsUrl = bucketVsUrl;
    }

    @Override
    public UserDTO updateProfilePic(UUID userId, String profilePic, UUID currentUUID) {
        if (!currentUUID.equals(userId))
            throw new AppException(
                    "Not allowed for this operation", AppStatusCode.UNAUTHORIZED_ERROR);

        UserDTO byUserId = userService.getUserById(userId);
        if (byUserId == null)
            throw new AppException(
                    "User not found with this user id", AppStatusCode.INVALID_REQUEST);
        UserDTO userDTO = userService.updateProfilePic(profilePic, userId);
        String profilePicUrl = getUrl(userDTO.getProfilePic());
        String coverPicUrl = getUrl(userDTO.getCoverPic());
        return cloneUserDTO(userDTO, profilePicUrl, coverPicUrl);
    }

    @Override
    public UserDTO updateCoverPic(UUID userId, String coverPic, UUID currentUUID) {

        if (!currentUUID.equals(userId))
            throw new AppException(
                    "Not allowed for this operation", AppStatusCode.UNAUTHORIZED_ERROR);

        UserDTO byUserId = userService.getUserById(userId);
        if (byUserId == null)
            throw new AppException(
                    "User not found with this user id", AppStatusCode.INVALID_REQUEST);
        UserDTO userDTO = userService.updateCoverPic(coverPic, userId);
        String profilePicUrl = getUrl(userDTO.getProfilePic());
        String coverPicUrl = getUrl(userDTO.getCoverPic());
        return cloneUserDTO(userDTO, profilePicUrl, coverPicUrl);
    }

    @Override
    public UserDTO getUserById(UUID id) {
        UserDTO userById = userService.getUserById(id);
        return cloneUserDTO(
                userById, getUrl(userById.getProfilePic()), getUrl(userById.getCoverPic()));
    }

    @Override
    public UserDTO getByFirebaseUid(String firebaseUid) {
        UserDTO userById = userService.getByFirebaseUid(firebaseUid);
        return cloneUserDTO(
                userById, getUrl(userById.getProfilePic()), getUrl(userById.getCoverPic()));
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if (!userDTO.getEmailVerified()) {
            throw new AppException("Email not verified", AppStatusCode.ACCESS_DENIED);
        }
        userDTO.setCoverPic(null);
        userDTO.setProfilePic(null);
        return userService.createUser(userDTO);
    }

    @Override
    public UserDTO createOrUpdate(UserDTO userDTO) {
        UserDTO update = userService.createOrUpdate(userDTO);
        return cloneUserDTO(update, getUrl(update.getProfilePic()), getUrl(update.getCoverPic()));
    }

    @Override
    public PageDTO<UserDTO> findAll(List<UUID> id, Integer page, Integer count, String sort) {
        if (page == null) page = 0;
        if (count == null) count = 10;
        PageDTO<UserDTO> all = null;
        if (id != null) {
            all = userService.findAll(id, page, count, sort);
        } else {
            all = userService.findAll(page, count, sort);
        }

        all.getContent()
                .forEach(
                        userDTO -> {
                            userDTO.setProfilePic(getUrl(userDTO.getProfilePic()));
                            userDTO.setCoverPic(getUrl(userDTO.getCoverPic()));
                        });
        return all;
    }

    @Override
    public UserDTO createAdmin(UUID userId) {
        return userService.createAdmin(userId);
    }

    private UserDTO cloneUserDTO(UserDTO userDTO, String url, String coverPicUrl) {
        UserDTO dto =
                UserDTO.builder()
                        .authorities(userDTO.getAuthorities())
                        .description(userDTO.getDescription())
                        .email(userDTO.getEmail())
                        .emailVerified(userDTO.getEmailVerified())
                        .firebaseUid(userDTO.getFirebaseUid())
                        .name(userDTO.getName())
                        .password(userDTO.getPassword())
                        .providerId(userDTO.getProviderId())
                        .phoneNumber(userDTO.getPhoneNumber())
                        .profilePic(url)
                        .coverPic(coverPicUrl)
                        .build();
        dto.setCreatedAt(userDTO.getCreatedAt());
        dto.setUpdatedAt(userDTO.getUpdatedAt());
        dto.setId(userDTO.getId());
        dto.setCreatedBy(userDTO.getCreatedBy());
        dto.setModifiedBy(userDTO.getModifiedBy());
        return dto;
    }

    private String getUrl(String bucketName) {
        if (bucketName == null) return null;
        String url = bucketVsUrl.get(bucketName);
        if (url == null) {
            ResponseEntity<BaseResponse<String>> coverUrlResponse =
                    mediaClient.getUrl(bucketName, 1L, TimeUnit.DAYS);
            if (coverUrlResponse.getStatusCode() == HttpStatus.OK
                    && coverUrlResponse.getBody() != null
                    && coverUrlResponse.getBody().getStatus() == Status.SUCCESS) {
                url = coverUrlResponse.getBody().getData();
                bucketVsUrl.put(bucketName, url);
            } else {
                throw new AppException("Media client fails", AppStatusCode.MEDIA_ERROR);
            }
        }
        return url;
    }
}
