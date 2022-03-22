package com.justyoga.place;

import com.justyoga.place.config.AuditorAwareImpl;
import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class PlaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlaceApplication.class, args);
    }

    @Bean
    AuditorAware<UUID> auditorProvider() {
        return new AuditorAwareImpl();
    }
}
