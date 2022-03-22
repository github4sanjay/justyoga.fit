package com.justyoga.gateway.service.impl;

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
}
