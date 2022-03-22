package com.justyoga.profile.web.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    public static final String MODEL_MAPPER = "ModelMapperWeb";

    @Bean(name = MODEL_MAPPER)
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        return mapper;
    }
}
