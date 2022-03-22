package com.justyoga.auth.service.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.SessionCookieOptions;
import com.justyoga.auth.config.auth.firebase.FirebaseTokenHolder;
import com.justyoga.auth.service.interfaces.FirebaseService;
import com.justyoga.auth.service.shared.FirebaseParser;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;

@Service
public class FirebaseServiceImpl implements FirebaseService {

    @Override
    public FirebaseTokenHolder parseToken(String firebaseToken) {
        return new FirebaseParser().parseToken(firebaseToken);
    }

    @Override
    public FirebaseTokenHolder parseToken(FirebaseToken firebaseToken) {
        return new FirebaseTokenHolder(firebaseToken);
    }

    @Override
    public FirebaseTokenHolder getFirebaseTokenHolder(String xAuth, Boolean checkRevoked)
            throws FirebaseAuthException {
        //        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifySessionCookie(
        //                xAuth, checkRevoked);
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(xAuth, checkRevoked);
        return this.parseToken(decodedToken);
    }

    @Override
    public String getSessionCookie(String idToken) throws FirebaseAuthException {
        long expiresIn = TimeUnit.DAYS.toMillis(5); // Set session expiration to 5 days.
        SessionCookieOptions options =
                SessionCookieOptions.builder().setExpiresIn(expiresIn).build();
        return FirebaseAuth.getInstance().createSessionCookie(idToken, options);
    }
}
