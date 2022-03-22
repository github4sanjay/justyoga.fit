package com.justyoga.profile.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    public StartupApplicationListener() {}

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {}
}
