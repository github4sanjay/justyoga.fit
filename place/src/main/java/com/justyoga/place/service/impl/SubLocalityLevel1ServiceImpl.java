package com.justyoga.place.service.impl;

import com.justyoga.place.cache.service.impl.core.SubLocalityLevel1Cache;
import com.justyoga.place.domain.dao.SubLocalityLevel1Repo;
import com.justyoga.place.domain.model.mysql.SubLocalityLevel1;
import com.justyoga.place.domain.model.mysql.SubLocalityLevel2;
import com.justyoga.place.service.interfaces.SubLocalityLevel1Service;
import com.justyoga.place.service.interfaces.SubLocalityLevel2Service;
import com.justyoga.place.web.config.WebConfig;
import com.justyoga.util.dto.cache.GenericCacheDTO;
import com.justyoga.util.dto.place.SubLocalityLevel1DTO;
import java.util.*;
import java.util.stream.Stream;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SubLocalityLevel1ServiceImpl implements SubLocalityLevel1Service {

    private final SubLocalityLevel1Repo subLocalityLevel1Repo;
    private final SubLocalityLevel1Cache subLocalityLevel1Cache;
    private final SubLocalityLevel2Service subLocalityLevel2Service;
    private final ModelMapper modelMapper;

    @Autowired
    public SubLocalityLevel1ServiceImpl(
            SubLocalityLevel1Repo subLocalityLevel1Repo,
            SubLocalityLevel1Cache subLocalityLevel1Cache,
            SubLocalityLevel2Service subLocalityLevel2Service,
            @Qualifier(WebConfig.MODEL_MAPPER) ModelMapper modelMapper) {
        this.subLocalityLevel1Repo = subLocalityLevel1Repo;
        this.subLocalityLevel1Cache = subLocalityLevel1Cache;
        this.subLocalityLevel2Service = subLocalityLevel2Service;
        this.modelMapper = modelMapper;
    }

    void updateCache(SubLocalityLevel1DTO dto) {
        GenericCacheDTO<UUID, SubLocalityLevel1DTO, UUID, UUID> cacheDTO = new GenericCacheDTO<>();
        cacheDTO.setData(dto);
        cacheDTO.setChildren(new LinkedHashSet<>());
        cacheDTO.setIdentifier(dto.getId());
        cacheDTO.setParent(dto.getLocalityId());
        subLocalityLevel1Cache.put(dto.getId(), cacheDTO);
    }

    @Override
    public List<SubLocalityLevel1DTO> getAllSubLocalityLevel1() {
        Collection<GenericCacheDTO<UUID, SubLocalityLevel1DTO, UUID, UUID>> all =
                subLocalityLevel1Cache.getAll();
        List<SubLocalityLevel1DTO> list = new ArrayList<>();
        if (all != null) {
            all.forEach(
                    dto -> {
                        list.add(dto.getData());
                    });
        } else {
            subLocalityLevel1Repo
                    .streamAllSubLocalityLevel1()
                    .forEach(
                            country -> {
                                list.add(modelMapper.map(country, SubLocalityLevel1DTO.class));
                            });
        }
        return list;
    }

    @Override
    public SubLocalityLevel1DTO findById(UUID id) {
        GenericCacheDTO<UUID, SubLocalityLevel1DTO, UUID, UUID> dtoCacheById =
                getSubLocalityLevel1DTOCacheById(id);
        return dtoCacheById.getData();
    }

    @Override
    public SubLocalityLevel1DTO getSubLocalityLevel1ByNameAndLocalityId(
            String name, UUID localityId) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Illegal SubLocality Level1 name.");
        if (localityId == null) throw new IllegalArgumentException("Illegal locality id.");
        SubLocalityLevel1 byNameAndLocalityId =
                subLocalityLevel1Repo.findByNameAndLocalityId(name, localityId);
        if (byNameAndLocalityId == null) return null;
        return modelMapper.map(byNameAndLocalityId, SubLocalityLevel1DTO.class);
    }

    @Override
    public SubLocalityLevel1DTO save(SubLocalityLevel1DTO dto) {
        if (dto == null
                || dto.getName() == null
                || dto.getName().isEmpty()
                || dto.getLocalityId() == null)
            throw new IllegalArgumentException("Illegal sub locality level1.");

        SubLocalityLevel1 save =
                subLocalityLevel1Repo.save(modelMapper.map(dto, SubLocalityLevel1.class));
        dto = modelMapper.map(save, SubLocalityLevel1DTO.class);
        updateCache(dto);
        return dto;
    }

    @Override
    public boolean addChildrenToSubLocalityLevel1Cache(UUID id, UUID subLocalityLevel2Id) {
        GenericCacheDTO<UUID, SubLocalityLevel1DTO, UUID, UUID> cacheDTO =
                subLocalityLevel1Cache.get(id);
        if (cacheDTO == null) return false;
        Set<UUID> children = cacheDTO.getChildren();
        if (children == null) return false;
        children.add(subLocalityLevel2Id);
        return true;
    }

    @Override
    public Stream<SubLocalityLevel1> streamAllByLocalityIdIn(Collection<UUID> localityPublicIds) {
        return subLocalityLevel1Repo.streamAllByLocalityIdIn(localityPublicIds);
    }

    @Override
    public List<SubLocalityLevel1> getSubLocalityLevel1ByLocalityId(UUID localityPublicId) {
        if (localityPublicId == null) return Collections.emptyList();
        return subLocalityLevel1Repo.findByLocalityId(localityPublicId);
    }

    @Override
    public GenericCacheDTO<UUID, SubLocalityLevel1DTO, UUID, UUID> getSubLocalityLevel1DTOCacheById(
            UUID publicId) {
        if (publicId == null)
            throw new IllegalArgumentException("Invalid Sub Locality Level 1 id.");
        GenericCacheDTO<UUID, SubLocalityLevel1DTO, UUID, UUID> subLocalityLevel1CacheDTO =
                subLocalityLevel1Cache.get(publicId);
        if (subLocalityLevel1CacheDTO != null) {
            return subLocalityLevel1CacheDTO;
        } else {
            subLocalityLevel1CacheDTO = new GenericCacheDTO<>();
            Optional<SubLocalityLevel1> byPublicId = subLocalityLevel1Repo.findById(publicId);
            if (byPublicId.isEmpty())
                throw new IllegalArgumentException("Invalid administrative area level 1 id.");
            Set<UUID> sL1PublicIds = new LinkedHashSet<>();
            SubLocalityLevel1DTO administrativeAreaLevel1DTO =
                    modelMapper.map(byPublicId, SubLocalityLevel1DTO.class);
            List<SubLocalityLevel2> allBySubLocalityLevel1PublicId =
                    subLocalityLevel2Service.findAllBySubLocalityLevel1Id(publicId);
            allBySubLocalityLevel1PublicId.forEach(
                    subLocalityLevel2 -> sL1PublicIds.add(subLocalityLevel2.getId()));
            subLocalityLevel1CacheDTO.setData(administrativeAreaLevel1DTO);
            subLocalityLevel1CacheDTO.setChildren(sL1PublicIds);
            subLocalityLevel1CacheDTO.setParent(administrativeAreaLevel1DTO.getLocalityId());
            subLocalityLevel1Cache.put(publicId, subLocalityLevel1CacheDTO);
        }
        return null;
    }
}
