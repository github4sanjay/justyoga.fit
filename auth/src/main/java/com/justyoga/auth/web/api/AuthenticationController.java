package com.justyoga.auth.web.api;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.justyoga.auth.constants.Constants;
import com.justyoga.auth.service.interfaces.AuthenticationService;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import com.justyoga.util.response.BaseResponse;
import com.justyoga.util.response.Status;
import io.swagger.annotations.Api;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Api(value = "Authentication APIs")
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/sign-in")
    @ResponseBody
    public ResponseEntity<BaseResponse<String>> signInByFirebaseUid(
            @RequestHeader("X-ID-TOKEN") String idToken, HttpServletResponse response) {
        try {
            String sessionCookie = authenticationService.signIn(idToken);
            response.addHeader(Constants.X_AUTHORIZATION_FIREBASE, sessionCookie);
            return new ResponseEntity<>(
                    new BaseResponse<>(Status.SUCCESS, sessionCookie), HttpStatus.OK);
        } catch (FirebaseAuthException e) {
            log.error("FirebaseAuthException", e);
            throw new AppException(e.getMessage(), AppStatusCode.INVALID_TOKEN);
        }
    }

    @PostMapping(value = "/auth")
    @ResponseBody
    public ResponseEntity<BaseResponse<String>> auth(
            @RequestHeader("X-Authorization-Firebase") String authToken,
            HttpServletResponse response) {
        String userId = authenticationService.authenticate(authToken);
        return new ResponseEntity<>(new BaseResponse<>(Status.SUCCESS, userId), HttpStatus.OK);
    }

    @PostMapping("/logout")
    @ResponseBody
    public ResponseEntity<BaseResponse<String>> clearSessionCookieAndRevoke(
            @RequestHeader("X-Authorization-Firebase") String authToken) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifySessionCookie(authToken);
            FirebaseAuth.getInstance().revokeRefreshTokens(decodedToken.getUid());
            return new ResponseEntity<>(new BaseResponse<>(Status.SUCCESS, null), HttpStatus.OK);
        } catch (FirebaseAuthException e) {
            log.error("FirebaseAuthException", e);
            throw new AppException(e.getMessage(), AppStatusCode.INVALID_TOKEN);
        }
    }
}
