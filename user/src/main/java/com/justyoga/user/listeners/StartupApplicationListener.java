package com.justyoga.user.listeners;

import com.justyoga.user.cache.service.impl.core.UserCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final UserCacheService userCacheService;

    @Autowired
    public StartupApplicationListener(UserCacheService userCacheService) {
        this.userCacheService = userCacheService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Loading user cache started.");
        userCacheService.initialLoad();
        log.info("Loading user cache finished.");
    }
}
