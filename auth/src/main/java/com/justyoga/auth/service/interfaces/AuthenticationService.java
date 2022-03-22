package com.justyoga.auth.service.interfaces;

import com.google.firebase.auth.FirebaseAuthException;

public interface AuthenticationService {
    String authenticate(String authToken);

    String signIn(String idToken) throws FirebaseAuthException;
}
