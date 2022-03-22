package com.justyoga.user.config;

import com.justyoga.util.annotation.UserContext;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;

@Slf4j
public class AuditorAwareImpl implements AuditorAware<UUID> {

    @NonNull
    @Override
    public Optional<UUID> getCurrentAuditor() {
        UserContext current = UserContext.current();
        UUID userId = current.getUserId();
        log.info("user id {}", userId);
        if (userId == null) return Optional.empty();
        return Optional.of(userId);
    }
}
