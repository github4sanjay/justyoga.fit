package com.justyoga.client.user;

import com.justyoga.util.dto.user.RoleDTO;
import com.justyoga.util.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user")
public interface RoleClient {

    @GetMapping(value = "/api/v1/role")
    ResponseEntity<BaseResponse<RoleDTO>> getRole(@RequestParam("authority") String authority);
}
