package com.justyoga.auth.service.shared;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.justyoga.auth.config.auth.firebase.FirebaseTokenHolder;
import com.justyoga.auth.util.StringUtil;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;

public class FirebaseParser {
    public FirebaseTokenHolder parseToken(String idToken) {
        if (StringUtil.isBlank(idToken)) {
            throw new IllegalArgumentException("Firebase Token Blank");
        }
        try {
            FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            return new FirebaseTokenHolder(firebaseToken);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), AppStatusCode.INVALID_ID_TOKEN);
        }
    }
}
