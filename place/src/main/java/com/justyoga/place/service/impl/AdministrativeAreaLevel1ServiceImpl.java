package com.justyoga.place.service.impl;

import com.justyoga.place.cache.service.impl.core.AdministrativeLevel1Cache;
import com.justyoga.place.domain.dao.AdministrativeAreaLevel1Repo;
import com.justyoga.place.domain.model.mysql.AdministrativeAreaLevel1;
import com.justyoga.place.domain.model.mysql.Locality;
import com.justyoga.place.service.interfaces.AdministrativeAreaLevel1Service;
import com.justyoga.place.service.interfaces.LocalityService;
import com.justyoga.place.web.config.WebConfig;
import com.justyoga.util.dto.cache.GenericCacheDTO;
import com.justyoga.util.dto.place.AdministrativeAreaLevel1DTO;
import java.util.*;
import java.util.stream.Stream;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AdministrativeAreaLevel1ServiceImpl implements AdministrativeAreaLevel1Service {

    private final AdministrativeAreaLevel1Repo administrativeAreaLevel1Repo;
    private final AdministrativeLevel1Cache administrativeLevel1Cache;
    private final ModelMapper modelMapper;
    private final LocalityService localityService;

    @Autowired
    public AdministrativeAreaLevel1ServiceImpl(
            AdministrativeAreaLevel1Repo administrativeAreaLevel1Repo,
            AdministrativeLevel1Cache administrativeLevel1Cache,
            @Qualifier(WebConfig.MODEL_MAPPER) ModelMapper modelMapper,
            LocalityService localityService) {
        this.administrativeAreaLevel1Repo = administrativeAreaLevel1Repo;
        this.administrativeLevel1Cache = administrativeLevel1Cache;
        this.modelMapper = modelMapper;
        this.localityService = localityService;
    }

    void updateCache(AdministrativeAreaLevel1DTO dto) {
        GenericCacheDTO<UUID, AdministrativeAreaLevel1DTO, UUID, UUID> cacheDTO =
                new GenericCacheDTO<>();
        cacheDTO.setData(dto);
        cacheDTO.setChildren(new LinkedHashSet<>());
        cacheDTO.setIdentifier(dto.getId());
        cacheDTO.setParent(dto.getCountryId());
        administrativeLevel1Cache.put(dto.getId(), cacheDTO);
    }

    @Override
    public List<AdministrativeAreaLevel1DTO> getAllAdministrativeAreaLevel1() {
        Collection<GenericCacheDTO<UUID, AdministrativeAreaLevel1DTO, UUID, UUID>> all =
                administrativeLevel1Cache.getAll();
        List<AdministrativeAreaLevel1DTO> list = new ArrayList<>();
        if (all != null) {
            all.forEach(
                    dto -> {
                        list.add(dto.getData());
                    });
        } else {
            administrativeAreaLevel1Repo
                    .streamAllAdministrativeAreaLevel1()
                    .forEach(
                            country -> {
                                list.add(
                                        modelMapper.map(
                                                country, AdministrativeAreaLevel1DTO.class));
                            });
        }
        return list;
    }

    @Override
    public AdministrativeAreaLevel1DTO getAdministrativeAreaLevel1NameAndCountryId(
            String name, UUID countryId) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Illegal administrative area level1 name.");
        if (countryId == null) throw new IllegalArgumentException("Illegal country id.");
        AdministrativeAreaLevel1 byName =
                administrativeAreaLevel1Repo.findByNameAndCountryId(name, countryId);
        if (byName == null) return null;
        return modelMapper.map(byName, AdministrativeAreaLevel1DTO.class);
    }

    @Override
    public AdministrativeAreaLevel1DTO save(AdministrativeAreaLevel1DTO dto) {
        if (dto == null
                || dto.getName() == null
                || dto.getName().isEmpty()
                || dto.getCountryId() == null)
            throw new IllegalArgumentException("Illegal administrative area level1.");

        AdministrativeAreaLevel1 save =
                administrativeAreaLevel1Repo.save(
                        modelMapper.map(dto, AdministrativeAreaLevel1.class));
        dto = modelMapper.map(save, AdministrativeAreaLevel1DTO.class);
        updateCache(dto);
        return dto;
    }

    @Override
    public boolean addChildrenToAdministrativeAreaLevel1Cache(UUID id, UUID localityId) {
        GenericCacheDTO<UUID, AdministrativeAreaLevel1DTO, UUID, UUID> cacheDTO =
                administrativeLevel1Cache.get(id);
        if (cacheDTO == null) return false;
        Set<UUID> children = cacheDTO.getChildren();
        if (children == null) return false;
        children.add(localityId);
        return true;
    }

    @Override
    public Stream<AdministrativeAreaLevel1> streamAllByCountryIdIn(List<UUID> countryPublicIds) {
        return administrativeAreaLevel1Repo.streamAllByCountryIdIn(countryPublicIds);
    }

    @Override
    public List<AdministrativeAreaLevel1> getAllByCountryId(UUID countryId) {
        if (countryId == null) return Collections.emptyList();
        return administrativeAreaLevel1Repo.findAllByCountryId(countryId);
    }

    @Override
    public GenericCacheDTO<UUID, AdministrativeAreaLevel1DTO, UUID, UUID>
            getAdministrativeAreaLevel1DTOCacheById(UUID id) {
        if (id == null)
            throw new IllegalArgumentException("Invalid Administrative Area Level 1 id.");
        GenericCacheDTO<UUID, AdministrativeAreaLevel1DTO, UUID, UUID>
                administrativeAreaLevel1CacheDTO = administrativeLevel1Cache.get(id);
        if (administrativeAreaLevel1CacheDTO != null) {
            return administrativeAreaLevel1CacheDTO;
        } else {
            administrativeAreaLevel1CacheDTO = new GenericCacheDTO<>();
            Optional<AdministrativeAreaLevel1> byPublicId =
                    administrativeAreaLevel1Repo.findById(id);
            if (byPublicId.isEmpty())
                throw new IllegalArgumentException("Invalid administrative area level 1 id.");
            Set<UUID> sL1PublicIds = new LinkedHashSet<>();
            AdministrativeAreaLevel1DTO administrativeAreaLevel1DTO =
                    modelMapper.map(byPublicId, AdministrativeAreaLevel1DTO.class);
            List<Locality> allByLocalityDTOPublicId =
                    localityService.findAllByAdministrativeAreaLevel1Id(id);
            allByLocalityDTOPublicId.forEach(
                    localityLevel1 -> sL1PublicIds.add(localityLevel1.getId()));
            administrativeAreaLevel1CacheDTO.setData(administrativeAreaLevel1DTO);
            administrativeAreaLevel1CacheDTO.setChildren(sL1PublicIds);
            administrativeAreaLevel1CacheDTO.setParent(administrativeAreaLevel1DTO.getCountryId());
            administrativeLevel1Cache.put(id, administrativeAreaLevel1CacheDTO);
        }
        return null;
    }
}
