package com.justyoga.client.auth;

import com.justyoga.util.response.BaseResponse;
import javax.servlet.http.HttpServletResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth")
public interface AuthClient {

    @GetMapping(value = "/api/v1/sign-in")
    ResponseEntity<BaseResponse<String>> signInByFirebaseUid(
            @RequestHeader(value = "X-ID-TOKEN") String idToken);

    @PostMapping(value = "/api/v1/auth")
    ResponseEntity<BaseResponse<String>> auth(
            @RequestHeader("X-Authorization-Firebase") String authToken);

    @PostMapping("/logout")
    ResponseEntity<BaseResponse<String>> clearSessionCookieAndRevoke(
            @RequestHeader("X-Authorization-Firebase") String authToken,
            HttpServletResponse response);
}
