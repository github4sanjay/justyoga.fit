package com.justyoga.bookmark;

import com.justyoga.bookmark.config.AuditorAwareImpl;
import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableFeignClients(basePackages = {"com.justyoga.client"})
public class BookmarkApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookmarkApplication.class, args);
    }

    @Bean
    AuditorAware<UUID> auditorProvider() {
        return new AuditorAwareImpl();
    }
}
