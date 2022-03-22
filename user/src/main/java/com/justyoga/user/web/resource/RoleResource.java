package com.justyoga.user.web.resource;

import com.justyoga.user.service.impl.RolesService;
import com.justyoga.util.dto.user.RoleDTO;
import com.justyoga.util.response.BaseResponse;
import com.justyoga.util.response.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class RoleResource {

    private final RolesService rolesService;

    @Autowired
    public RoleResource(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @GetMapping("/role")
    @ResponseBody
    public ResponseEntity<BaseResponse<RoleDTO>> getRole(
            @RequestParam(value = "authority") String authority) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, rolesService.getRole(authority)), HttpStatus.OK);
    }
}
