package com.justyoga.search.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.justyoga.util.dto.cache.CacheApi;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BucketVsUrl implements CacheApi<String, String> {

    private static final Cache<String, String> cache =
            CacheBuilder.newBuilder().expireAfterWrite(23, TimeUnit.HOURS).build();

    @Override
    public void put(String s, String cacheDTO) {
        cache.put(s, cacheDTO);
    }

    @Override
    public String get(String s) {
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
    public void putAll(Map<String, String> map) {
        cache.putAll(map);
    }

    @Override
    public Collection<String> getAll() {
        ConcurrentMap<String, String> stringGenericCacheDTOConcurrentMap = cache.asMap();
        return stringGenericCacheDTOConcurrentMap.values();
    }
}
