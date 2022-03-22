package com.justyoga.user.cache.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.justyoga.util.dto.cache.CacheApi;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class FirebaseUIDVsUUIDCacheService implements CacheApi<String, UUID> {
    private static final Cache<String, UUID> cache = CacheBuilder.newBuilder().build();

    @Override
    public void put(String s, UUID dto) {
        cache.put(s, dto);
    }

    @Override
    public UUID get(String s) {
        return cache.getIfPresent(s);
    }

    @Override
    public void clearAll() {
        cache.invalidateAll();
    }

    @Override
    public void clear(String s) {
        cache.invalidate(s);
    }

    @Transactional
    @Override
    public void initialLoad() {}

    @Override
    public void putAll(Map<String, UUID> map) {
        cache.putAll(map);
    }

    public Collection<UUID> getAll() {
        return cache.asMap().values();
    }
}
