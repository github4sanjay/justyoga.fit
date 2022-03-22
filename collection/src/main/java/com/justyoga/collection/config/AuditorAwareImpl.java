package com.justyoga.collection.config;

import com.justyoga.util.annotation.UserContext;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;

public class AuditorAwareImpl implements AuditorAware<UUID> {

    @NonNull
    @Override
    public Optional<UUID> getCurrentAuditor() {
        UserContext current = UserContext.current();
        UUID userId = current.getUserId();
        if (userId == null) return Optional.empty();
        return Optional.of(userId);
    }
}
