package com.justyoga.blog;

import com.justyoga.blog.config.AuditorAwareImpl;
import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.justyoga.client"})
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Bean
    AuditorAware<UUID> auditorProvider() {
        return new AuditorAwareImpl();
    }
}
