package com.justyoga.gateway.config.firebase;

import com.justyoga.gateway.service.impl.UserServiceImpl;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class FirebaseAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userService;

    @Autowired
    public FirebaseAuthenticationProvider(
            @Qualifier(value = UserServiceImpl.NAME) UserDetailsService userService) {
        this.userService = userService;
    }

    public boolean supports(Class<?> authentication) {
        return (FirebaseAuthenticationToken.class.isAssignableFrom(authentication));
    }

    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }

        FirebaseAuthenticationToken authenticationToken =
                (FirebaseAuthenticationToken) authentication;
        UserDetails details = userService.loadUserByUsername(authenticationToken.getName());
        if (details == null) {
            throw new AppException(AppStatusCode.USER_NOT_FOUND);
        }

        authenticationToken =
                new FirebaseAuthenticationToken(
                        details, authentication.getCredentials(), details.getAuthorities());

        return authenticationToken;
    }
}
