package com.justyoga.user.web.resource;

import com.justyoga.user.service.interfaces.UserService;
import com.justyoga.user.web.service.interfaces.ProfileService;
import com.justyoga.util.annotation.MaintainUserContext;
import com.justyoga.util.dto.user.UserDTO;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import com.justyoga.util.page.PageDTO;
import com.justyoga.util.response.BaseResponse;
import com.justyoga.util.response.Status;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class UserResource {

    private final UserService userService;
    private final ProfileService profileService;

    @Autowired
    public UserResource(UserService userService, ProfileService profileService) {
        this.userService = userService;
        this.profileService = profileService;
    }

    @GetMapping(value = "/users/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<UserDTO>> getUserByPublicId(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, profileService.getUserById(id)), HttpStatus.OK);
    }

    @GetMapping(value = "/users")
    @ResponseBody
    public ResponseEntity<BaseResponse<UserDTO>> getUserByFirebaseUId(
            @RequestParam(value = "firebaseUid", required = false) String firebaseUid,
            @RequestHeader(value = "X-Authorization-UUID", required = false) UUID id) {
        UserDTO user;
        if (firebaseUid != null) {
            user = profileService.getByFirebaseUid(firebaseUid);
        } else {
            user = profileService.getUserById(id);
        }
        return new ResponseEntity<>(new BaseResponse<>(Status.SUCCESS, user), HttpStatus.OK);
    }

    @GetMapping(value = "/user-collection")
    @ResponseBody
    public ResponseEntity<BaseResponse<PageDTO<UserDTO>>> findAll(
            @RequestParam(required = false) List<UUID> id,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer count,
            @RequestParam(required = false) String sort) {

        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, profileService.findAll(id, page, count, sort)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PostMapping(value = "/users")
    @ResponseBody
    public ResponseEntity<BaseResponse<UserDTO>> create(@RequestBody @Valid UserDTO userDTO) {
        return new ResponseEntity<>( // X-Authorization-UUID not required as user is created for the
                // first time
                new BaseResponse<>(Status.SUCCESS, profileService.createUser(userDTO)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PutMapping(value = "/users")
    @ResponseBody
    public ResponseEntity<BaseResponse<UserDTO>> createOrUpdate(
            @RequestBody @Valid UserDTO userDTO,
            @RequestHeader("X-Authorization-UUID") UUID userId) {
        if (!userId.equals(userDTO.getId())) {
            throw new AppException(
                    "Not authorized to do this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, profileService.createOrUpdate(userDTO)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PutMapping(value = "/users/{id}/admin")
    @ResponseBody
    public ResponseEntity<BaseResponse<UserDTO>> createAdmin(
            @PathVariable("id") UUID userId,
            @RequestHeader("X-Authorization-UUID") UUID currentUserId) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, profileService.createAdmin(userId)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PostMapping(value = "/users/{id}/profile-pic")
    @ResponseBody
    public ResponseEntity<BaseResponse<UserDTO>> updateProfilePic(
            @RequestBody @Valid Url url,
            @PathVariable("id") UUID id,
            @RequestHeader("X-Authorization-UUID") UUID userId) {
        UserDTO pic = profileService.updateProfilePic(id, url.getUrl(), userId);
        return new ResponseEntity<>(new BaseResponse<>(Status.SUCCESS, pic), HttpStatus.OK);
    }

    @MaintainUserContext
    @PostMapping(value = "/users/{id}/cover-pic")
    @ResponseBody
    public ResponseEntity<BaseResponse<UserDTO>> updateCoverPic(
            @RequestBody @Valid Url url,
            @PathVariable("id") UUID id,
            @RequestHeader("X-Authorization-UUID") UUID userId) {
        UserDTO pic = profileService.updateCoverPic(id, url.getUrl(), userId);
        return new ResponseEntity<>(new BaseResponse<>(Status.SUCCESS, pic), HttpStatus.OK);
    }

    @Data
    private static class Url {
        private String url;
    }

    @MaintainUserContext
    @DeleteMapping("/users")
    @ResponseBody
    public ResponseEntity<BaseResponse<String>> deleteUserBasedOnFirebaseUUID(
            @RequestParam("firebase-uuid") String firebaseUUID,
            @RequestHeader("X-Authorization-UUID") UUID userId) {

        userService.deleteUserByFirebaseUUID(firebaseUUID);
        return new ResponseEntity<>(new BaseResponse<>(Status.SUCCESS, null), HttpStatus.OK);
    }
}
