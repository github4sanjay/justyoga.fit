package com.justyoga.blog.config;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    @Profile("docker")
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(
                        Collections.singletonList(
                                new ParameterBuilder()
                                        .name("X-Authorization-Firebase")
                                        .description("Firebase authorization header")
                                        .modelRef(new ModelRef("string"))
                                        .parameterType("header")
                                        .build()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/api.*"))
                .build()
                .apiInfo(metaData());
    }

    @Bean
    @Profile("prod")
    public Docket apiProd() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(false)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo metaData() {
        return new ApiInfo(
                "JustYoga - Yoga trainging",
                "Rest apis for Blog backend",
                "1.0",
                "Terms of service",
                new Contact("Manish Kumar", "https://www.justyoga.fit", "doyog.fit@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                Collections.emptyList());
    }
}
