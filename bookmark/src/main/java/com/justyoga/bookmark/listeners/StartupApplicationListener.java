package com.justyoga.bookmark.listeners;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StartupApplicationListener {

    @Autowired
    public StartupApplicationListener() {}

    @PostConstruct
    public void init() {
        log.info("Loading review cache started.");
        log.info("Loading review cache finished.");
    }
}
