package com.justyoga.place.cache.service.impl;

import com.justyoga.place.cache.service.impl.core.*;
import com.justyoga.place.service.interfaces.*;
import com.justyoga.place.web.config.WebConfig;
import com.justyoga.util.dto.cache.CacheApi;
import com.justyoga.util.dto.cache.GenericCacheDTO;
import com.justyoga.util.dto.place.*;
import java.util.*;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LocationCacheService {

    private final ModelMapper modelMapper;

    private final CountryCache countryCache;
    private final AdministrativeLevel1Cache administrativeLevel1Cache;
    private final LocalityCache localityCache;
    private final SubLocalityLevel1Cache subLocalityLevel1Cache;
    private final SubLocalityLevel2Cache subLocalityLevel2Cache;

    private final AdministrativeAreaLevel1Service administrativeAreaLevel1Service;
    private final CountryService countryService;
    private final LocalityService localityService;
    private final SubLocalityLevel1Service subLocalityLevel1Service;
    private final SubLocalityLevel2Service subLocalityLevel2Service;

    @Autowired
    public LocationCacheService(
            @Qualifier(WebConfig.MODEL_MAPPER) ModelMapper modelMapper,
            CountryService countryService,
            CountryCache countryCache,
            AdministrativeLevel1Cache administrativeLevel1Cache,
            LocalityCache localityCache,
            SubLocalityLevel1Cache subLocalityLevel1Cache,
            SubLocalityLevel2Cache subLocalityLevel2Cache,
            AdministrativeAreaLevel1Service administrativeAreaLevel1Service,
            LocalityService localityService,
            SubLocalityLevel1Service subLocalityLevel1Service,
            SubLocalityLevel2Service subLocalityLevel2Service) {
        this.modelMapper = modelMapper;
        this.countryService = countryService;
        this.countryCache = countryCache;
        this.administrativeLevel1Cache = administrativeLevel1Cache;
        this.localityCache = localityCache;
        this.subLocalityLevel1Cache = subLocalityLevel1Cache;
        this.subLocalityLevel2Cache = subLocalityLevel2Cache;

        this.administrativeAreaLevel1Service = administrativeAreaLevel1Service;
        this.localityService = localityService;
        this.subLocalityLevel1Service = subLocalityLevel1Service;
        this.subLocalityLevel2Service = subLocalityLevel2Service;
    }

    @Transactional
    public void loadLocationCache() {
        Map<UUID, GenericCacheDTO<UUID, CountryDTO, UUID, UUID>> genericCountryCacheDTOMap =
                new HashMap<>();
        List<UUID> countryIds = new ArrayList<>();
        countryService
                .streamAllCountry()
                .forEach(
                        country -> {
                            GenericCacheDTO<UUID, CountryDTO, UUID, UUID> cacheDTO =
                                    new GenericCacheDTO<>();
                            cacheDTO.setData(modelMapper.map(country, CountryDTO.class));
                            cacheDTO.setIdentifier(country.getId());
                            genericCountryCacheDTOMap.put(country.getId(), cacheDTO);
                            countryIds.add(country.getId());
                        });

        Map<UUID, GenericCacheDTO<UUID, AdministrativeAreaLevel1DTO, UUID, UUID>>
                genericAL1CacheDTOMap = new HashMap<>();
        Map<UUID, Set<UUID>> countryIdVsAdministrativeLevel1ListMap = new HashMap<>();
        List<UUID> aL1Ids = new ArrayList<>();
        administrativeAreaLevel1Service
                .streamAllByCountryIdIn(countryIds)
                .forEach(
                        administrativeAreaLevel1 -> {
                            UUID id = administrativeAreaLevel1.getId();
                            GenericCacheDTO<UUID, AdministrativeAreaLevel1DTO, UUID, UUID>
                                    cacheDTO = new GenericCacheDTO<>();
                            cacheDTO.setData(
                                    modelMapper.map(
                                            administrativeAreaLevel1,
                                            AdministrativeAreaLevel1DTO.class));
                            cacheDTO.setIdentifier(id);
                            genericAL1CacheDTOMap.put(id, cacheDTO);
                            aL1Ids.add(administrativeAreaLevel1.getId());
                            countryIdVsAdministrativeLevel1ListMap
                                    .computeIfAbsent(
                                            administrativeAreaLevel1.getCountryId(),
                                            administrativeLevel1List -> new LinkedHashSet<>())
                                    .add(administrativeAreaLevel1.getId());
                        });

        Map<UUID, GenericCacheDTO<UUID, LocalityDTO, UUID, UUID>> genericLocalityCacheDTOMap =
                new HashMap<>();
        Map<UUID, Set<UUID>> aL1IdVsLocalityMap = new HashMap<>();
        List<UUID> localityIds = new ArrayList<>();
        localityService
                .streamAllByAdministrativeAreaLevel1IdIn(aL1Ids)
                .forEach(
                        locality -> {
                            UUID id = locality.getId();
                            GenericCacheDTO<UUID, LocalityDTO, UUID, UUID> cacheDTO =
                                    new GenericCacheDTO<>();
                            cacheDTO.setData(modelMapper.map(locality, LocalityDTO.class));
                            cacheDTO.setIdentifier(id);
                            genericLocalityCacheDTOMap.put(id, cacheDTO);
                            localityIds.add(locality.getId());
                            aL1IdVsLocalityMap
                                    .computeIfAbsent(
                                            locality.getAdministrativeAreaLevel1Id(),
                                            localityList -> new LinkedHashSet<>())
                                    .add(locality.getId());
                        });

        Map<UUID, GenericCacheDTO<UUID, SubLocalityLevel1DTO, UUID, UUID>> genericSL1CacheDTOMap =
                new HashMap<>();
        Map<UUID, Set<UUID>> sL1IdVsLocalityMap = new HashMap<>();
        List<UUID> sL1Ids = new ArrayList<>();
        subLocalityLevel1Service
                .streamAllByLocalityIdIn(localityIds)
                .forEach(
                        subLocality -> {
                            UUID id = subLocality.getId();
                            GenericCacheDTO<UUID, SubLocalityLevel1DTO, UUID, UUID> cacheDTO =
                                    new GenericCacheDTO<>();
                            cacheDTO.setData(
                                    modelMapper.map(subLocality, SubLocalityLevel1DTO.class));
                            cacheDTO.setIdentifier(id);
                            genericSL1CacheDTOMap.put(id, cacheDTO);
                            sL1Ids.add(subLocality.getId());
                            sL1IdVsLocalityMap
                                    .computeIfAbsent(
                                            subLocality.getLocalityId(),
                                            localityList -> new LinkedHashSet<>())
                                    .add(subLocality.getId());
                        });

        Map<UUID, GenericCacheDTO<UUID, SubLocalityLevel2DTO, UUID, UUID>> genericSL2CacheDTOMap =
                new HashMap<>();
        Map<UUID, Set<UUID>> sL2IdVsSL1Map = new HashMap<>();
        subLocalityLevel2Service
                .streamAllBySubLocalityLevel1IdIn(sL1Ids)
                .forEach(
                        subLocalityLevel2 -> {
                            UUID id = subLocalityLevel2.getId();
                            GenericCacheDTO<UUID, SubLocalityLevel2DTO, UUID, UUID> cacheDTO =
                                    new GenericCacheDTO<>();
                            cacheDTO.setData(
                                    modelMapper.map(subLocalityLevel2, SubLocalityLevel2DTO.class));
                            cacheDTO.setIdentifier(id);
                            genericSL2CacheDTOMap.put(id, cacheDTO);
                            sL2IdVsSL1Map
                                    .computeIfAbsent(
                                            subLocalityLevel2.getSubLocalityLevel1Id(),
                                            localityList -> new LinkedHashSet<>())
                                    .add(subLocalityLevel2.getId());
                        });

        this.setCache(
                genericCountryCacheDTOMap,
                null,
                countryIdVsAdministrativeLevel1ListMap,
                countryCache);
        this.setCache(
                genericAL1CacheDTOMap,
                countryIdVsAdministrativeLevel1ListMap,
                aL1IdVsLocalityMap,
                administrativeLevel1Cache);
        this.setCache(
                genericLocalityCacheDTOMap, aL1IdVsLocalityMap, sL1IdVsLocalityMap, localityCache);
        this.setCache(
                genericSL1CacheDTOMap, sL1IdVsLocalityMap, sL2IdVsSL1Map, subLocalityLevel1Cache);
        this.setCache(genericSL2CacheDTOMap, sL2IdVsSL1Map, null, subLocalityLevel2Cache);
    }

    private <D> void setCache(
            Map<UUID, GenericCacheDTO<UUID, D, UUID, UUID>> genericCacheDTOMap,
            Map<UUID, Set<UUID>> idVsLocalityMap,
            Map<UUID, Set<UUID>> sL2IdVsSL1Map,
            CacheApi<UUID, GenericCacheDTO<UUID, D, UUID, UUID>> cacheApi) {
        genericCacheDTOMap.forEach(
                (sL1Id, genericCacheDTO) -> {
                    if (idVsLocalityMap != null) {
                        idVsLocalityMap.forEach(
                                (aLong, longs) -> {
                                    if (longs.contains(sL1Id)) genericCacheDTO.setParent(aLong);
                                });
                    }
                    if (sL2IdVsSL1Map != null) {
                        genericCacheDTO.setChildren(sL2IdVsSL1Map.get(sL1Id));
                    }
                    cacheApi.put(sL1Id, genericCacheDTO);
                });
    }
}
