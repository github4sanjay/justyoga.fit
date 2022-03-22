package com.justyoga.place.service.impl;

import com.justyoga.place.cache.service.impl.core.LocalityCache;
import com.justyoga.place.domain.dao.LocalityRepo;
import com.justyoga.place.domain.model.mysql.Locality;
import com.justyoga.place.domain.model.mysql.SubLocalityLevel1;
import com.justyoga.place.service.interfaces.LocalityService;
import com.justyoga.place.service.interfaces.SubLocalityLevel1Service;
import com.justyoga.place.web.config.WebConfig;
import com.justyoga.util.dto.cache.GenericCacheDTO;
import com.justyoga.util.dto.place.LocalityDTO;
import java.util.*;
import java.util.stream.Stream;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LocalityServiceImpl implements LocalityService {

    private final LocalityRepo localityRepo;
    private final LocalityCache localityCache;
    private final ModelMapper modelMapper;
    private final SubLocalityLevel1Service subLocalityLevel1Service;

    @Autowired
    public LocalityServiceImpl(
            LocalityRepo localityRepo,
            LocalityCache localityCache,
            @Qualifier(WebConfig.MODEL_MAPPER) ModelMapper modelMapper,
            SubLocalityLevel1Service subLocalityLevel1Service) {
        this.localityRepo = localityRepo;
        this.localityCache = localityCache;
        this.modelMapper = modelMapper;
        this.subLocalityLevel1Service = subLocalityLevel1Service;
    }

    void updateCache(LocalityDTO dto) {
        GenericCacheDTO<UUID, LocalityDTO, UUID, UUID> cacheDTO = new GenericCacheDTO<>();
        cacheDTO.setData(dto);
        cacheDTO.setChildren(new LinkedHashSet<>());
        cacheDTO.setIdentifier(dto.getId());
        cacheDTO.setParent(dto.getAdministrativeAreaLevel1Id());
        localityCache.put(dto.getId(), cacheDTO);
    }

    @Override
    public List<LocalityDTO> getAllLocality() {
        Collection<GenericCacheDTO<UUID, LocalityDTO, UUID, UUID>> all = localityCache.getAll();
        List<LocalityDTO> list = new ArrayList<>();
        if (all != null) {
            all.forEach(
                    dto -> {
                        list.add(dto.getData());
                    });
        } else {
            localityRepo
                    .streamAllLocality()
                    .forEach(
                            country -> {
                                list.add(modelMapper.map(country, LocalityDTO.class));
                            });
        }
        return list;
    }

    @Override
    public LocalityDTO findById(UUID id) {
        GenericCacheDTO<UUID, LocalityDTO, UUID, UUID> localityCacheById = getLocalityCacheById(id);
        return localityCacheById.getData();
    }

    @Override
    public LocalityDTO getLocalityByNameAndAdministrativeAreaLevel1Id(
            String name, UUID administrativeAreaLevel1Id) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Illegal locality name.");
        if (administrativeAreaLevel1Id == null)
            throw new IllegalArgumentException("Illegal administrativeAreaLevel1 id.");
        Locality locality =
                localityRepo.findByNameAndAdministrativeAreaLevel1Id(
                        name, administrativeAreaLevel1Id);
        if (locality == null) return null;
        return modelMapper.map(locality, LocalityDTO.class);
    }

    @Override
    public LocalityDTO save(LocalityDTO dto) {
        if (dto == null
                || dto.getName() == null
                || dto.getName().isEmpty()
                || dto.getAdministrativeAreaLevel1Id() == null)
            throw new IllegalArgumentException("Illegal locality");
        Locality save = localityRepo.save(modelMapper.map(dto, Locality.class));
        dto = modelMapper.map(save, LocalityDTO.class);
        updateCache(dto);
        return dto;
    }

    @Override
    public boolean addChildrenToLocalityCache(UUID id, UUID subLocalityLevel1Id) {
        GenericCacheDTO<UUID, LocalityDTO, UUID, UUID> cacheDTO = localityCache.get(id);
        if (cacheDTO == null) return false;
        Set<UUID> children = cacheDTO.getChildren();
        if (children == null) return false;
        children.add(subLocalityLevel1Id);
        return true;
    }

    @Override
    public GenericCacheDTO<UUID, LocalityDTO, UUID, UUID> getLocalityCacheById(UUID id) {
        if (id == null) throw new IllegalArgumentException("Invalid locality id.");
        GenericCacheDTO<UUID, LocalityDTO, UUID, UUID> localityCacheDTO = localityCache.get(id);
        if (localityCacheDTO != null) {
            return localityCacheDTO;
        } else {
            localityCacheDTO = new GenericCacheDTO<>();
            Optional<Locality> byPublicId = localityRepo.findById(id);
            if (byPublicId.isEmpty()) throw new IllegalArgumentException("Invalid locality id.");
            Set<UUID> sL1PublicIds = new LinkedHashSet<>();
            LocalityDTO localityDTO = modelMapper.map(byPublicId, LocalityDTO.class);
            List<SubLocalityLevel1> allByLocalityDTOPublicId =
                    subLocalityLevel1Service.getSubLocalityLevel1ByLocalityId(id);
            allByLocalityDTOPublicId.forEach(
                    localityLevel1 -> sL1PublicIds.add(localityLevel1.getId()));
            localityCacheDTO.setData(localityDTO);
            localityCacheDTO.setChildren(sL1PublicIds);
            localityCacheDTO.setParent(localityDTO.getAdministrativeAreaLevel1Id());
            localityCache.put(id, localityCacheDTO);
        }
        return null;
    }

    @Override
    public List<Locality> findAllByAdministrativeAreaLevel1Id(UUID administrativeAreaLevel1Id) {
        if (administrativeAreaLevel1Id == null) return Collections.emptyList();
        return localityRepo.findAllByAdministrativeAreaLevel1Id(administrativeAreaLevel1Id);
    }

    @Override
    public Stream<Locality> streamAllByAdministrativeAreaLevel1IdIn(Collection<UUID> ids) {
        return localityRepo.streamAllByAdministrativeAreaLevel1IdIn(ids);
    }
}
