package com.justyoga.place.service.impl;

import com.justyoga.place.cache.service.impl.core.CountryCache;
import com.justyoga.place.domain.dao.CountryRepo;
import com.justyoga.place.domain.model.mysql.AdministrativeAreaLevel1;
import com.justyoga.place.domain.model.mysql.Country;
import com.justyoga.place.service.interfaces.AdministrativeAreaLevel1Service;
import com.justyoga.place.service.interfaces.CountryService;
import com.justyoga.place.web.config.WebConfig;
import com.justyoga.util.dto.cache.GenericCacheDTO;
import com.justyoga.util.dto.place.CountryDTO;
import java.util.*;
import java.util.stream.Stream;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepo countryRepo;
    private final CountryCache countryCache;
    private final ModelMapper modelMapper;
    private final AdministrativeAreaLevel1Service administrativeAreaLevel1Service;

    @Autowired
    public CountryServiceImpl(
            CountryRepo countryRepo,
            CountryCache countryCache,
            @Qualifier(WebConfig.MODEL_MAPPER) ModelMapper modelMapper,
            AdministrativeAreaLevel1ServiceImpl administrativeAreaLevel1Service) {
        this.countryRepo = countryRepo;
        this.countryCache = countryCache;
        this.modelMapper = modelMapper;
        this.administrativeAreaLevel1Service = administrativeAreaLevel1Service;
    }

    public void updateCache(CountryDTO dto) {
        GenericCacheDTO<UUID, CountryDTO, UUID, UUID> cacheDTO = new GenericCacheDTO<>();
        cacheDTO.setData(dto);
        cacheDTO.setChildren(new LinkedHashSet<>());
        cacheDTO.setIdentifier(dto.getId());
        countryCache.put(dto.getId(), cacheDTO);
    }

    @Override
    public CountryDTO getCountryByName(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Illegal country name.");
        Country byName = countryRepo.findByName(name);
        if (byName == null) return null;
        return modelMapper.map(byName, CountryDTO.class);
    }

    @Override
    public CountryDTO save(CountryDTO dto) {
        if (dto == null || dto.getName() == null)
            throw new IllegalArgumentException("Illegal country name.");
        Country country = modelMapper.map(dto, Country.class);
        Country save = countryRepo.save(country);
        dto = modelMapper.map(save, CountryDTO.class);
        updateCache(dto);
        return dto;
    }

    @Override
    public boolean addChildrenToCountryCache(UUID id, UUID administrativeAreaLevel1Id) {
        GenericCacheDTO<UUID, CountryDTO, UUID, UUID> cacheDTO = countryCache.get(id);
        if (cacheDTO == null) return false;
        Set<UUID> children = cacheDTO.getChildren();
        if (children == null) return false;
        children.add(administrativeAreaLevel1Id);
        return true;
    }

    @Override
    public Stream<Country> streamAllCountry() {
        return countryRepo.streamAllCountry();
    }

    @Override
    public List<CountryDTO> getAllCountry() {
        Collection<GenericCacheDTO<UUID, CountryDTO, UUID, UUID>> all = countryCache.getAll();
        List<CountryDTO> list = new ArrayList<>();
        if (all != null) {
            all.forEach(
                    dto -> {
                        list.add(dto.getData());
                    });
        } else {
            this.streamAllCountry()
                    .forEach(
                            country -> {
                                list.add(modelMapper.map(country, CountryDTO.class));
                            });
        }
        return list;
    }

    @Override
    public GenericCacheDTO<UUID, CountryDTO, UUID, UUID> getCountryCacheById(UUID publicId) {
        if (publicId == null) throw new IllegalArgumentException("Invalid country id.");
        GenericCacheDTO<UUID, CountryDTO, UUID, UUID> countryCacheDTO = countryCache.get(publicId);
        if (countryCacheDTO != null) {
            return countryCacheDTO;
        } else {
            countryCacheDTO = new GenericCacheDTO<>();
            Optional<Country> byPublicId = countryRepo.findById(publicId);
            if (byPublicId.isEmpty()) throw new IllegalArgumentException("Invalid country id.");
            Set<UUID> aal1PublicIds = new LinkedHashSet<>();
            CountryDTO countryDTO = modelMapper.map(byPublicId, CountryDTO.class);
            List<AdministrativeAreaLevel1> allByCountryPublicId =
                    administrativeAreaLevel1Service.getAllByCountryId(publicId);
            allByCountryPublicId.forEach(
                    administrativeAreaLevel1 ->
                            aal1PublicIds.add(administrativeAreaLevel1.getId()));
            countryCacheDTO.setData(countryDTO);
            countryCacheDTO.setChildren(aal1PublicIds);
            countryCache.put(publicId, countryCacheDTO);
            return countryCacheDTO;
        }
    }

    @Override
    public CountryDTO getCountryById(UUID id) {
        if (id == null) throw new IllegalArgumentException("Invalid country id.");
        GenericCacheDTO<UUID, CountryDTO, UUID, UUID> countryCacheDTO = countryCache.get(id);
        if (countryCacheDTO != null) {
            return countryCacheDTO.getData();
        } else {
            Optional<Country> byPublicId = countryRepo.findById(id);
            if (byPublicId.isEmpty()) {
                return modelMapper.map(byPublicId, CountryDTO.class);
            } else throw new IllegalArgumentException("Invalid country id.");
        }
    }
}
