package com.justyoga.place.service.impl;

import com.justyoga.place.cache.service.impl.core.SubLocalityLevel2Cache;
import com.justyoga.place.domain.dao.SubLocalityLevel2Repo;
import com.justyoga.place.domain.model.mysql.SubLocalityLevel2;
import com.justyoga.place.service.interfaces.SubLocalityLevel2Service;
import com.justyoga.place.web.config.WebConfig;
import com.justyoga.util.dto.cache.GenericCacheDTO;
import com.justyoga.util.dto.place.SubLocalityLevel2DTO;
import java.util.*;
import java.util.stream.Stream;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SubLocalityLevel2ServiceImpl implements SubLocalityLevel2Service {

    private final SubLocalityLevel2Repo subLocalityLevel2Repo;
    private final SubLocalityLevel2Cache subLocalityLevel2Cache;
    private final ModelMapper modelMapper;

    @Autowired
    public SubLocalityLevel2ServiceImpl(
            SubLocalityLevel2Repo subLocalityLevel2Repo,
            SubLocalityLevel2Cache subLocalityLevel2Cache,
            @Qualifier(WebConfig.MODEL_MAPPER) ModelMapper modelMapper) {
        this.subLocalityLevel2Repo = subLocalityLevel2Repo;
        this.subLocalityLevel2Cache = subLocalityLevel2Cache;
        this.modelMapper = modelMapper;
    }

    void updateCache(SubLocalityLevel2DTO dto) {
        GenericCacheDTO<UUID, SubLocalityLevel2DTO, UUID, UUID> cacheDTO = new GenericCacheDTO<>();
        cacheDTO.setData(dto);
        cacheDTO.setChildren(new LinkedHashSet<>());
        cacheDTO.setIdentifier(dto.getId());
        cacheDTO.setParent(dto.getSubLocalityLevel1Id());
        subLocalityLevel2Cache.put(dto.getId(), cacheDTO);
    }

    @Override
    public List<SubLocalityLevel2DTO> getAllSubLocalityLevel2() {
        Collection<GenericCacheDTO<UUID, SubLocalityLevel2DTO, UUID, UUID>> all =
                subLocalityLevel2Cache.getAll();
        List<SubLocalityLevel2DTO> list = new ArrayList<>();
        if (all != null) {
            all.forEach(
                    dto -> {
                        list.add(dto.getData());
                    });
        } else {
            subLocalityLevel2Repo
                    .streamAllSubLocalityLevel2()
                    .forEach(
                            country -> {
                                list.add(modelMapper.map(country, SubLocalityLevel2DTO.class));
                            });
        }
        return list;
    }

    @Override
    public SubLocalityLevel2DTO getSubLocalityLevel2ByNameAndSubLocalityLevel1Id(
            String name, UUID subLocalityLevel1Id) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Illegal SubLocality Level2 name.");
        if (subLocalityLevel1Id == null)
            throw new IllegalArgumentException("Illegal subLocalityLevel1 id.");
        SubLocalityLevel2 byName =
                subLocalityLevel2Repo.findByNameAndSubLocalityLevel1Id(name, subLocalityLevel1Id);
        if (byName == null) return null;
        return modelMapper.map(byName, SubLocalityLevel2DTO.class);
    }

    @Override
    public SubLocalityLevel2DTO save(SubLocalityLevel2DTO dto) {
        if (dto == null
                || dto.getName() == null
                || dto.getName().isEmpty()
                || dto.getSubLocalityLevel1Id() == null)
            throw new IllegalArgumentException("Illegal sub locality level2.");

        SubLocalityLevel2 save =
                subLocalityLevel2Repo.save(modelMapper.map(dto, SubLocalityLevel2.class));
        dto = modelMapper.map(save, SubLocalityLevel2DTO.class);
        updateCache(dto);
        return dto;
    }

    @Override
    public List<SubLocalityLevel2> findAllBySubLocalityLevel1Id(UUID subLocalityLevel1PublicId) {
        if (subLocalityLevel1PublicId == null) return Collections.emptyList();
        return subLocalityLevel2Repo.findAllBySubLocalityLevel1Id(subLocalityLevel1PublicId);
    }

    @Override
    public Stream<SubLocalityLevel2> streamAllBySubLocalityLevel1IdIn(
            Collection<UUID> localityPublicIds) {
        return subLocalityLevel2Repo.streamAllBySubLocalityLevel1IdIn(localityPublicIds);
    }
}
