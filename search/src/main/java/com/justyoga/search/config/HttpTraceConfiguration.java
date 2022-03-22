package com.justyoga.search.config;

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
