package com.justyoga.user.cache.service.impl.core;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.justyoga.user.cache.service.FirebaseUIDVsUUIDCacheService;
import com.justyoga.user.domain.dao.UserRepository;
import com.justyoga.user.web.config.WebConfig;
import com.justyoga.util.dto.cache.CacheApi;
import com.justyoga.util.dto.user.UserDTO;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserCacheService implements CacheApi<UUID, UserDTO> {

    private static final Cache<UUID, UserDTO> cache = CacheBuilder.newBuilder().build();

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final FirebaseUIDVsUUIDCacheService firebaseUIDVsUUIDCacheService;

    @Autowired
    public UserCacheService(
            UserRepository userRepository,
            @Qualifier(WebConfig.MODEL_MAPPER) ModelMapper modelMapper,
            FirebaseUIDVsUUIDCacheService firebaseUIDVsUUIDCacheService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.firebaseUIDVsUUIDCacheService = firebaseUIDVsUUIDCacheService;
    }

    @Override
    public void put(UUID id, UserDTO userCacheDTO) {
        cache.put(id, userCacheDTO);
    }

    @Override
    public UserDTO get(UUID id) {
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

    @Transactional
    @Override
    public void initialLoad() {
        Map<UUID, UserDTO> userCacheDTOMap = new HashMap<>();

        userRepository
                .streamAllUser()
                .forEach(
                        user -> {
                            userCacheDTOMap.put(user.getId(), modelMapper.map(user, UserDTO.class));
                            firebaseUIDVsUUIDCacheService.put(user.getFirebaseUid(), user.getId());
                        });
        cache.putAll(userCacheDTOMap);
    }

    @Override
    public void putAll(Map<UUID, UserDTO> map) {
        cache.putAll(map);
    }

    public Collection<UserDTO> getAll() {
        ConcurrentMap<UUID, UserDTO> stringGenericCacheDTOConcurrentMap = cache.asMap();
        return stringGenericCacheDTOConcurrentMap.values();
    }
}
