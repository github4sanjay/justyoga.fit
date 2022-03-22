package com.justyoga.auth.config;

import com.justyoga.util.config.HttpTraceLogFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpTraceConfiguration {

    @Bean
    public HttpTraceLogFilter httpTraceLogFilter() {
        return new HttpTraceLogFilter();
    }
}
