package com.justyoga.auth.service.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.justyoga.auth.config.auth.firebase.FirebaseTokenHolder;
import com.justyoga.auth.service.interfaces.AuthenticationService;
import com.justyoga.auth.service.interfaces.FirebaseService;
import com.justyoga.client.user.UserClient;
import com.justyoga.util.dto.user.UserDTO;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final FirebaseService firebaseService;
    private final UserClient userClient;

    @Autowired
    public AuthenticationServiceImpl(FirebaseService firebaseService, UserClient userClient) {
        this.firebaseService = firebaseService;
        this.userClient = userClient;
    }

    @Override
    public String authenticate(String authToken) {
        FirebaseTokenHolder firebaseTokenHolder = null;
        try {
            firebaseTokenHolder = firebaseService.getFirebaseTokenHolder(authToken, true);
            return firebaseTokenHolder.getUid();
        } catch (FirebaseAuthException e) {
            throw new AppException(e.getMessage(), AppStatusCode.INVALID_TOKEN);
        }
    }

    @Override
    public String signIn(String idToken) throws FirebaseAuthException {
        // String sessionCookie = firebaseService.getSessionCookie(idToken);
        FirebaseTokenHolder firebaseTokenHolder =
                firebaseService.getFirebaseTokenHolder(idToken, true);
        UserRecord userRecord = FirebaseAuth.getInstance().getUser(firebaseTokenHolder.getUid());
        if (!userRecord.isEmailVerified()) {
            throw new AppException("Email not verified", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setFirebaseUid(userRecord.getUid());
        userDTO.setEmail(userRecord.getEmail());
        userDTO.setPhoneNumber(userRecord.getPhoneNumber());
        userDTO.setProfilePic(userRecord.getPhotoUrl());
        userDTO.setName(userRecord.getDisplayName());
        userDTO.setEmailVerified(true);
        userDTO.setProviderId(userRecord.getProviderId());
        userDTO.setPassword(UUID.randomUUID().toString());
        try {
            userClient.create(userDTO);
        } catch (AppException e) {
            // nothing to do
        }
        return idToken;
    }
}
