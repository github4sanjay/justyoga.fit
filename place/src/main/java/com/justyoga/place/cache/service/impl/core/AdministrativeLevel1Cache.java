package com.justyoga.place.cache.service.impl.core;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.justyoga.util.dto.cache.CacheApi;
import com.justyoga.util.dto.cache.GenericCacheDTO;
import com.justyoga.util.dto.place.AdministrativeAreaLevel1DTO;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import org.springframework.stereotype.Component;

/**
 * Overview:
 *
 * <p>For storing administrative level cache and it parent i.e country and its children i.e
 * localities Initial Loaded in {@link LocationCacheService}
 *
 * <p>
 */
@Component
public class AdministrativeLevel1Cache
        implements CacheApi<UUID, GenericCacheDTO<UUID, AdministrativeAreaLevel1DTO, UUID, UUID>> {

    private static final Cache<UUID, GenericCacheDTO<UUID, AdministrativeAreaLevel1DTO, UUID, UUID>>
            cache = CacheBuilder.newBuilder().build();

    @Override
    public void put(
            UUID id,
            GenericCacheDTO<UUID, AdministrativeAreaLevel1DTO, UUID, UUID> genericCacheDTO) {
        cache.put(id, genericCacheDTO);
    }

    @Override
    public GenericCacheDTO<UUID, AdministrativeAreaLevel1DTO, UUID, UUID> get(UUID id) {
        return cache.getIfPresent(id);
    }

    @Override
    public void clearAll() {
        cache.invalidateAll();
    }

    @Override
    public void clear(UUID id) {
        cache.invalidate(id);
    }

    @Override
    public void initialLoad() {}

    @Override
    public void putAll(
            Map<UUID, GenericCacheDTO<UUID, AdministrativeAreaLevel1DTO, UUID, UUID>> map) {
        cache.putAll(map);
    }

    @Override
    public Collection<GenericCacheDTO<UUID, AdministrativeAreaLevel1DTO, UUID, UUID>> getAll() {
        ConcurrentMap<UUID, GenericCacheDTO<UUID, AdministrativeAreaLevel1DTO, UUID, UUID>>
                stringGenericCacheDTOConcurrentMap = cache.asMap();
        return stringGenericCacheDTOConcurrentMap.values();
    }
}
