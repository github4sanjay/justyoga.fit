package com.justyoga.gateway.service.impl;

import com.justyoga.client.user.UserClient;
import com.justyoga.gateway.config.RoleGrantedAuthority;
import com.justyoga.gateway.config.WebConfig;
import com.justyoga.gateway.service.interfaces.UserService;
import com.justyoga.util.dto.user.RoleDTO;
import com.justyoga.util.dto.user.UserDTO;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.response.BaseResponse;
import java.lang.reflect.Type;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = UserServiceImpl.NAME)
@Slf4j
public class UserServiceImpl implements UserService {

    public static final String NAME = "UserService";

    private final UserClient userClient;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(
            UserClient userClient, @Qualifier(WebConfig.MODEL_MAPPER) ModelMapper modelMapper) {
        this.userClient = userClient;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.isEmpty())
            throw new UsernameNotFoundException("User does not exist.");
        try {
            ResponseEntity<BaseResponse<UserDTO>> userByFirebaseUId =
                    userClient.getUserByFirebaseUId(username);
            BaseResponse<UserDTO> body = userByFirebaseUId.getBody();
            if (body != null) {
                UserDTO userDTO = body.getData();
                if (userDTO == null) {
                    throw new UsernameNotFoundException("User does not exist.");
                }
                List<RoleDTO> authorities = userDTO.getAuthorities();
                Type listType = new TypeToken<List<RoleGrantedAuthority>>() {}.getType();
                List<RoleGrantedAuthority> roleGrantedAuthorities =
                        modelMapper.map(authorities, listType);
                return new org.springframework.security.core.userdetails.User(
                        userDTO.getId().toString(), userDTO.getPassword(), roleGrantedAuthorities);
            } else {
                throw new UsernameNotFoundException("User does not exist.");
            }
        } catch (AppException e) {
            log.info("Error when calling user service getUserByFirebaseUId", e);
            throw new UsernameNotFoundException("User does not exist.");
        }
    }
}
