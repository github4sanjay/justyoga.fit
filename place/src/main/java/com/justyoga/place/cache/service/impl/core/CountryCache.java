package com.justyoga.place.cache.service.impl.core;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.justyoga.util.dto.cache.CacheApi;
import com.justyoga.util.dto.cache.GenericCacheDTO;
import com.justyoga.util.dto.place.CountryDTO;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import org.springframework.stereotype.Component;

@Component
public class CountryCache implements CacheApi<UUID, GenericCacheDTO<UUID, CountryDTO, UUID, UUID>> {
    private static final Cache<UUID, GenericCacheDTO<UUID, CountryDTO, UUID, UUID>> countryCache =
            CacheBuilder.newBuilder().build();

    @Override
    public void put(UUID countryId, GenericCacheDTO<UUID, CountryDTO, UUID, UUID> genericCacheDTO) {
        countryCache.put(countryId, genericCacheDTO);
    }

    @Override
    public GenericCacheDTO<UUID, CountryDTO, UUID, UUID> get(UUID countryId) {
        return countryCache.getIfPresent(countryId);
    }

    @Override
    public void clearAll() {
        countryCache.invalidateAll();
    }

    @Override
    public void clear(UUID countryId) {
        countryCache.invalidate(countryId);
    }

    @Override
    public void initialLoad() {}

    @Override
    public void putAll(Map<UUID, GenericCacheDTO<UUID, CountryDTO, UUID, UUID>> map) {
        countryCache.putAll(map);
    }

    public Collection<GenericCacheDTO<UUID, CountryDTO, UUID, UUID>> getAll() {
        ConcurrentMap<UUID, GenericCacheDTO<UUID, CountryDTO, UUID, UUID>>
                stringGenericCacheDTOConcurrentMap = countryCache.asMap();
        return stringGenericCacheDTOConcurrentMap.values();
    }
}
