package com.justyoga.user.service.impl;

import com.justyoga.user.domain.dao.RoleRepository;
import com.justyoga.user.domain.model.mysql.RoleEntity;
import com.justyoga.user.web.config.WebConfig;
import com.justyoga.util.dto.user.RoleDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RolesService {

    public static final String ANONYMOUS = "ANONYMOUS";
    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";

    private static final String ROLE_ = "ROLE_";
    public static final String ROLE_ANONYMOUS = ROLE_ + ANONYMOUS;
    public static final String ROLE_USER = ROLE_ + USER;
    public static final String ROLE_ADMIN = ROLE_ + ADMIN;

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RolesService(
            RoleRepository roleRepository,
            @Qualifier(WebConfig.MODEL_MAPPER) ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    public List<RoleEntity> getAdminRoles() {
        List<RoleEntity> list = new ArrayList<>();
        list.add(getRoleType(ROLE_ADMIN));
        return list;
    }

    public List<RoleEntity> getUserRoles() {
        return Collections.singletonList(getRoleType(ROLE_USER));
    }

    /**
     * Get or create role
     *
     * @param authority describes the role
     * @return roleEntity
     */
    private RoleEntity getRoleType(String authority) {
        RoleEntity adminRole = roleRepository.findByAuthority(authority);
        if (adminRole == null) {
            return new RoleEntity(authority);
        } else {
            return adminRole;
        }
    }

    public RoleDTO getRole(String authority) {
        RoleEntity adminRole = roleRepository.findByAuthority(authority);
        if (adminRole == null) {
            return modelMapper.map(new RoleEntity(authority), RoleDTO.class);
        } else {
            return modelMapper.map(adminRole, RoleDTO.class);
        }
    }
}
