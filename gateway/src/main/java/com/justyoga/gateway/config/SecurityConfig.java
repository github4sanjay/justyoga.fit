package com.justyoga.gateway.config;

import com.justyoga.client.auth.AuthClient;
import com.justyoga.gateway.config.firebase.FirebaseAuthenticationProvider;
import com.justyoga.gateway.config.firebase.FirebaseFilter;
import com.justyoga.gateway.service.impl.RolesService;
import com.justyoga.gateway.service.impl.UserServiceImpl;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Configuration
    protected static class AuthenticationSecurity extends GlobalAuthenticationConfigurerAdapter {

        @Value("${fit.justyoga.firebase.enabled}")
        private Boolean firebaseEnabled;

        private final FirebaseAuthenticationProvider firebaseProvider;
        private final UserDetailsService userService;

        @Autowired
        public AuthenticationSecurity(
                @Qualifier(value = UserServiceImpl.NAME) UserDetailsService userService,
                FirebaseAuthenticationProvider firebaseProvider) {
            this.userService = userService;
            this.firebaseProvider = firebaseProvider;
        }

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userService);
            if (firebaseEnabled) {
                auth.authenticationProvider(firebaseProvider);
            }
        }
    }

    @Configuration
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

        @Value("${fit.justyoga.firebase.enabled}")
        private Boolean firebaseEnabled;

        private final AuthClient authClient;

        @Autowired(required = false)
        public ApplicationSecurity(AuthClient authClient) {
            this.authClient = authClient;
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring()
                    .antMatchers(
                            "/v2/api-docs",
                            "/configuration/ui",
                            "/swagger-resources",
                            "/configuration/security",
                            "/swagger-ui.html",
                            "/webjars/**",
                            "/v2/swagger.json");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers(
                            "/v2/api-docs",
                            "/swagger-resources",
                            "/swagger-resources/configuration/ui",
                            "/swagger-resources/configuration/security")
                    .permitAll();

            if (firebaseEnabled) {
                http.cors()
                        .and()
                        .addFilterBefore(
                                tokenAuthorizationFilter(authClient),
                                BasicAuthenticationFilter.class)
                        .authorizeRequests() //
                        .antMatchers("/api/open/**")
                        .hasAnyRole(
                                RolesService.ANONYMOUS, RolesService.USER, RolesService.ADMIN) //
                        .antMatchers("/api/client/**")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers("/api/admin/**")
                        .hasAnyRole(RolesService.ADMIN) //
                        .antMatchers("/health/**")
                        .hasAnyRole(RolesService.ADMIN) //
                        .antMatchers("/auth/api/v1/sign-in")
                        .hasAnyRole(
                                RolesService.ANONYMOUS, RolesService.USER, RolesService.ADMIN) //
                        .antMatchers("/auth/api/v1/logout")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers("/auth/api/v1/auth")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.PUT, "/user/api/v1/users")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.GET, "/user/api/v1/users/{\\d+}")
                        .hasAnyRole(
                                RolesService.ANONYMOUS, RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.GET, "/user/api/v1/users")
                        .hasAnyRole(
                                RolesService.ANONYMOUS, RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.POST, "/user/api/v1/users/{\\d+}/profile-pic")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.POST, "/user/api/v1/users/{\\d+}/cover-pic")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.DELETE, "/user/api/v1/users")
                        .hasAnyRole(RolesService.ADMIN) //
                        .antMatchers("/location/api/v1/reverse-geocode")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers("/location/api/v1/autocomplete-results")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers("/location/api/v1/place-details")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers("/place/api/v1/places")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.GET, "/profile/api/v1/basic-info/{\\d+}")
                        .hasAnyRole(
                                RolesService.ANONYMOUS, RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.GET, "/profile/api/v1/basic-info")
                        .hasAnyRole(
                                RolesService.ANONYMOUS, RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.PUT, "/profile/api/v1/basic-info")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.GET, "/profile/api/v1/interests/{\\d+}")
                        .hasAnyRole(
                                RolesService.ANONYMOUS, RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.GET, "/profile/api/v1/interests")
                        .hasAnyRole(
                                RolesService.ANONYMOUS, RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.PUT, "/profile/api/v1/interests")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.POST, "/profile/api/v1/images")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.PUT, "/profile/api/v1/images")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.DELETE, "/profile/api/v1/images/{\\d+}")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.POST, "/profile/api/v1/videos")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.PUT, "/profile/api/v1/videos")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.DELETE, "/profile/api/v1/videos/{\\d+}")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.GET, "/profile/api/v1/medical-expertise")
                        .hasAnyRole(
                                RolesService.ANONYMOUS, RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.POST, "/profile/api/v1/medical-expertise")
                        .hasAnyRole(RolesService.ADMIN) //
                        .antMatchers(
                                HttpMethod.POST, "/profile/api/v1/medical-expertise-collection")
                        .hasAnyRole(RolesService.ADMIN) //
                        .antMatchers(HttpMethod.DELETE, "/profile/api/v1/medical-expertise/{\\d+}")
                        .hasAnyRole(RolesService.ADMIN) //
                        .antMatchers(
                                HttpMethod.DELETE, "/profile/api/v1/user-medical-expertise/{\\d+}")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.POST, "/profile/api/v1/user-medical-expertise")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.GET, "/profile/api/v1/user-medical-expertise")
                        .hasAnyRole(
                                RolesService.ANONYMOUS, RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.GET, "/profile/api/v1/yoga-certificate")
                        .hasAnyRole(
                                RolesService.ANONYMOUS, RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.POST, "/profile/api/v1/yoga-certificate")
                        .hasAnyRole(RolesService.ADMIN) //
                        .antMatchers(HttpMethod.POST, "/profile/api/v1/yoga-certificate-collection")
                        .hasAnyRole(RolesService.ADMIN) //
                        .antMatchers(HttpMethod.DELETE, "/profile/api/v1/yoga-certificate/{\\d+}")
                        .hasAnyRole(RolesService.ADMIN) //
                        .antMatchers(
                                HttpMethod.DELETE, "/profile/api/v1/user-yoga-certificate/{\\d+}")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.POST, "/profile/api/v1/user-yoga-certificate")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.GET, "/profile/api/v1/user-yoga-certificate")
                        .hasAnyRole(
                                RolesService.ANONYMOUS, RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.GET, "/profile/api/v1/yoga-expertise")
                        .hasAnyRole(
                                RolesService.ANONYMOUS, RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.POST, "/profile/api/v1/yoga-expertise")
                        .hasAnyRole(RolesService.ADMIN) //
                        .antMatchers(HttpMethod.POST, "/profile/api/v1/yoga-expertise-collection")
                        .hasAnyRole(RolesService.ADMIN) //
                        .antMatchers(HttpMethod.DELETE, "/profile/api/v1/yoga-expertise/{\\d+}")
                        .hasAnyRole(RolesService.ADMIN) //
                        .antMatchers(
                                HttpMethod.DELETE, "/profile/api/v1/user-yoga-expertise/{\\d+}")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.POST, "/profile/api/v1/user-yoga-expertise")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.GET, "/profile/api/v1/user-yoga-expertise")
                        .hasAnyRole(
                                RolesService.ANONYMOUS, RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.PUT, "/review/api/v1/reviews")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.DELETE, "/review/api/v1/reviews/{\\d+}")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.PUT, "/review/api/v1/likes")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.DELETE, "/review/api/v1/likes/{\\d+}")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.PUT, "/review/api/v1/comments")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.DELETE, "/review/api/v1/comments/{\\d+}")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.POST, "/review/api/v1/reviews/{\\d+}/images")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.DELETE, "/review/api/v1/images/{\\d+}")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.POST, "/review/api/v1/reviews/{\\d+}/videos")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.DELETE, "/review/api/v1/videos/{id}")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.PUT, "/blog/api/v1/blogs")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.DELETE, "/blog/api/v1/blogs/{\\d+}")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.PUT, "/blog/api/v1/likes")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.DELETE, "/blog/api/v1/likes/{\\d+}")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.PUT, "/blog/api/v1/comments")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.DELETE, "/blog/api/v1/comments/{\\d+}")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.POST, "/blog/api/v1/blogs/{\\d+}/images")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.DELETE, "/blog/api/v1/images/{\\d+}")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.POST, "/blog/api/v1/blogs/{\\d+}/videos")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.DELETE, "/blog/api/v1/videos/{id}")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.PUT, "/blog/api/v1/blogs/{\\d+}/cover")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.PUT, "/collection/api/v1/collections")
                        .hasAnyRole(RolesService.ADMIN) //
                        .antMatchers(HttpMethod.DELETE, "/collection/api/v1/collections/{\\d+}")
                        .hasAnyRole(RolesService.ADMIN) //
                        .antMatchers(HttpMethod.PUT, "/collection/api/v1/collections/{\\d+}/cover")
                        .hasAnyRole(RolesService.ADMIN) //
                        .antMatchers(HttpMethod.PUT, "/collection/api/v1/collection-images")
                        .hasAnyRole(RolesService.ADMIN) //
                        .antMatchers(
                                HttpMethod.DELETE, "/collection/api/v1/collection-images/{\\d+}")
                        .hasAnyRole(RolesService.ADMIN) //
                        .antMatchers(HttpMethod.PUT, "/collection/api/v1/collection-videos")
                        .hasAnyRole(RolesService.ADMIN) //
                        .antMatchers(
                                HttpMethod.DELETE, "/collection/api/v1/collection-videos/{\\d+}")
                        .hasAnyRole(RolesService.ADMIN) //
                        .antMatchers(HttpMethod.PUT, "/collection/api/v1/collection-blogs")
                        .hasAnyRole(RolesService.ADMIN) //
                        .antMatchers(
                                HttpMethod.DELETE, "/collection/api/v1/collection-blogs/{\\d+}")
                        .hasAnyRole(RolesService.ADMIN) //
                        .antMatchers(HttpMethod.POST, "/library/api/v1/media-upload")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.POST, "/library/api/v1/media-upload/url/public")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers(HttpMethod.POST, "/library/api/v1/media-upload/url")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //

                        // .antMatchers("/**").denyAll()//
                        .and()
                        .csrf()
                        .disable() //
                        .anonymous()
                        .authorities(RolesService.ROLE_ANONYMOUS); //
            } else {
                http.cors()
                        .and()
                        .httpBasic()
                        .and()
                        .authorizeRequests() //
                        .antMatchers("/api/open/**")
                        .hasAnyRole(
                                RolesService.ANONYMOUS, RolesService.USER, RolesService.ADMIN) //
                        .antMatchers("/api/client/**")
                        .hasAnyRole(RolesService.USER, RolesService.ADMIN) //
                        .antMatchers("/api/admin/**")
                        .hasAnyRole(RolesService.ADMIN) //
                        .antMatchers("/health/**")
                        .hasAnyRole(RolesService.ADMIN) //
                        // .antMatchers("/**").denyAll()//
                        .and()
                        .csrf()
                        .disable() //
                        .anonymous()
                        .authorities(RolesService.ROLE_ANONYMOUS); //
            }
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedHeaders(
                    Arrays.asList(
                            "Origin",
                            "X-Requested-With",
                            "Accept",
                            "X-Authorization-Firebase",
                            "Content-Type",
                            "content-type",
                            "X-ID-TOKEN"));
            configuration.setAllowedOrigins(
                    Arrays.asList(
                            "http://127.0.0.1:3000",
                            "http://localhost:3000",
                            "https://whispering-badlands-15150.herokuapp.com",
                            "http://192.168.1.81:3000",
                            "http://justyoga.fit:3000",
                            "http://justyoga.fit",
                            "https://justyoga.fit"));
            configuration.setAllowedMethods(
                    Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            configuration.setAllowCredentials(true);
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }

        private FirebaseFilter tokenAuthorizationFilter(AuthClient authClient) {
            return new FirebaseFilter(authClient);
        }
    }
}
