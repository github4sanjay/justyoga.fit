package com.justyoga.client.user;

import com.justyoga.util.dto.user.UserDTO;
import com.justyoga.util.page.PageDTO;
import com.justyoga.util.response.BaseResponse;
import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user")
public interface UserClient {

    @GetMapping(value = "/api/v1/users")
    ResponseEntity<BaseResponse<UserDTO>> getUserByFirebaseUId(
            @RequestParam("firebaseUid") String id);

    @PutMapping(value = "/api/v1/users")
    ResponseEntity<BaseResponse<UserDTO>> createOrUpdate(
            @RequestBody UserDTO userDTO, @RequestHeader("X-Authorization-UUID") UUID userId);

    @PostMapping(value = "/api/v1/users")
    ResponseEntity<BaseResponse<UserDTO>> create(@RequestBody UserDTO userDTO);

    @GetMapping(value = "/api/v1/user-collection")
    public ResponseEntity<BaseResponse<PageDTO<UserDTO>>> findAll(
            @RequestParam(value = "firebaseUid", required = false) String firebaseUid,
            @RequestParam(required = false) List<UUID> id,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer count,
            @RequestParam(required = false) String sort);
}
