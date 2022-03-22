package com.justyoga.auth.service.interfaces;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.justyoga.auth.config.auth.firebase.FirebaseTokenHolder;

public interface FirebaseService {

    FirebaseTokenHolder parseToken(String idToken);

    FirebaseTokenHolder parseToken(FirebaseToken firebaseToken);

    FirebaseTokenHolder getFirebaseTokenHolder(String xAuth, Boolean checkRevoked)
            throws FirebaseAuthException;

    String getSessionCookie(String idToken) throws FirebaseAuthException;
}
