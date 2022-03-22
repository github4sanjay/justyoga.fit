package com.justyoga.gateway.config.firebase;

import com.justyoga.client.auth.AuthClient;
import com.justyoga.util.response.BaseResponse;
import com.justyoga.util.string.StringUtil;
import feign.FeignException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class FirebaseFilter extends OncePerRequestFilter {

    private static final String HEADER_NAME = "X-Authorization-Firebase";

    private final AuthClient authClient;

    public FirebaseFilter(AuthClient authClient) {
        this.authClient = authClient;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getRequestURI().equals("/auth/api/v1/sign-in")) { // not required in sign in
            filterChain.doFilter(request, response);
            return;
        }

        String xAuth = request.getHeader(HEADER_NAME);
        /*if (xAuth == null) {
            Cookie cookie = CookieManager.getCookie(request, HEADER_NAME);
            if (cookie != null) {
                xAuth = cookie.getValue();
            }
        }*/
        if (StringUtil.isBlank(xAuth) || xAuth == null || xAuth.equals("null")) {
            filterChain.doFilter(request, response);
        } else {
            try {
                log.info("/auth called");
                ResponseEntity<BaseResponse<String>> authResponse = authClient.auth(xAuth);
                if (authResponse.getStatusCode() == HttpStatus.OK
                        && authResponse.getBody() != null) {
                    String userName = authResponse.getBody().getData();
                    Authentication auth = new FirebaseAuthenticationToken(userName, null);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
                filterChain.doFilter(request, response);
            } catch (FeignException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }
}
