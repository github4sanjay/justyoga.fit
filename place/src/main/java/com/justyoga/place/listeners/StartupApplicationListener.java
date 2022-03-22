package com.justyoga.place.listeners;

import com.justyoga.place.cache.service.impl.LocationCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(StartupApplicationListener.class);

    private final LocationCacheService locationCacheService;

    @Autowired
    public StartupApplicationListener(LocationCacheService locationCacheService) {
        this.locationCacheService = locationCacheService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        LOG.info("Loading location cache started.");
        locationCacheService.loadLocationCache();
        LOG.info("Loading location cache finished.");
    }
}
