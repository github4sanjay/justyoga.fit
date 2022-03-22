package com.justyoga.auth.web.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    public static final String MODEL_MAPPER = "ModelMapperWeb";

    @Bean(name = MODEL_MAPPER)
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        /*mapper.addConverter(new Converter<EPGenderMappingCacheDTO, EPGenderMappingDTO>() {
            public EPGenderMappingDTO convert(MappingContext<EPGenderMappingCacheDTO, EPGenderMappingDTO> context) {
                EPGenderMappingCacheDTO epGenderMappingCacheDTO = context.getSource();
                EPGenderMappingDTO epGenderMappingDTO = context.getDestination();
                epGenderMappingDTO.setGender(epGenderMappingCacheDTO.getGender().getName());
                epGenderMappingDTO.setEducationalPlaceId(epGenderMappingCacheDTO.getEducationalPlaceId());
                return epGenderMappingDTO;
            }
        });*/
        return mapper;
    }
}
