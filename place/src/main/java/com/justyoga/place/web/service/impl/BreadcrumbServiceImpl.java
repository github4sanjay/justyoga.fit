package com.justyoga.place.web.service.impl;

import com.justyoga.place.service.interfaces.*;
import com.justyoga.place.web.service.interfaces.BreadcrumbService;
import com.justyoga.util.dto.cache.GenericCacheDTO;
import com.justyoga.util.dto.place.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BreadcrumbServiceImpl implements BreadcrumbService {

    private static final String HOME = "Home";
    private static final String DIRECTORY = "/directory";
    private final CountryService countryService;
    private final LocalityService localityService;
    private final AdministrativeAreaLevel1Service administrativeAreaLevel1Service;
    private final SubLocalityLevel1Service subLocalityLevel1Service;

    @Autowired
    public BreadcrumbServiceImpl(
            CountryService countryService,
            LocalityService localityService,
            AdministrativeAreaLevel1Service administrativeAreaLevel1Service,
            SubLocalityLevel1Service subLocalityLevel1Service) {
        this.countryService = countryService;
        this.localityService = localityService;
        this.administrativeAreaLevel1Service = administrativeAreaLevel1Service;
        this.subLocalityLevel1Service = subLocalityLevel1Service;
    }

    @Override
    public List<BreadcrumbDTO> getBreadcrumbsForLocalityByCountryId(UUID countryId) {
        List<BreadcrumbDTO> breadcrumbDTOList = new ArrayList<>();
        GenericCacheDTO<UUID, CountryDTO, UUID, UUID> countryCacheDTO =
                countryService.getCountryCacheById(countryId);
        if (countryCacheDTO != null) {
            BreadcrumbDTO breadcrumbDTO = new BreadcrumbDTO(HOME, false, DIRECTORY);
            breadcrumbDTOList.add(breadcrumbDTO);

            BreadcrumbDTO breadcrumbDTO1 =
                    new BreadcrumbDTO(
                            countryCacheDTO.getData().getName(),
                            true,
                            DIRECTORY + "/" + countryCacheDTO.getIdentifier());
            breadcrumbDTOList.add(breadcrumbDTO1);
        }
        return breadcrumbDTOList;
    }

    @Override
    public List<BreadcrumbDTO> getBreadcrumbsForLocalityId(UUID localityId) {
        List<BreadcrumbDTO> breadcrumbDTOList = new ArrayList<>();

        GenericCacheDTO<UUID, LocalityDTO, UUID, UUID> localityCacheDTO =
                localityService.getLocalityCacheById(localityId);

        if (localityCacheDTO == null) return breadcrumbDTOList;

        GenericCacheDTO<UUID, AdministrativeAreaLevel1DTO, UUID, UUID>
                administrativeLevel1CacheDTO =
                        administrativeAreaLevel1Service.getAdministrativeAreaLevel1DTOCacheById(
                                localityCacheDTO.getParent());

        if (administrativeLevel1CacheDTO == null) return breadcrumbDTOList;

        GenericCacheDTO<UUID, CountryDTO, UUID, UUID> countryCacheDTO =
                countryService.getCountryCacheById(administrativeLevel1CacheDTO.getParent());

        if (countryCacheDTO != null) {
            BreadcrumbDTO breadcrumbDTO = new BreadcrumbDTO(HOME, false, DIRECTORY);
            breadcrumbDTOList.add(breadcrumbDTO);

            BreadcrumbDTO breadcrumbDTO1 =
                    new BreadcrumbDTO(
                            countryCacheDTO.getData().getName(),
                            false,
                            DIRECTORY + "/" + countryCacheDTO.getIdentifier());

            breadcrumbDTOList.add(breadcrumbDTO1);

            BreadcrumbDTO breadcrumbDTO2 =
                    new BreadcrumbDTO(
                            localityCacheDTO.getData().getName(),
                            true,
                            DIRECTORY
                                    + "/"
                                    + countryCacheDTO.getIdentifier()
                                    + "/"
                                    + localityCacheDTO.getIdentifier());

            breadcrumbDTOList.add(breadcrumbDTO2);
        }

        return breadcrumbDTOList;
    }

    @Override
    public List<BreadcrumbDTO> getBreadcrumbsForSL1Id(UUID sL1Id) {
        List<BreadcrumbDTO> breadcrumbDTOList = new ArrayList<>();

        GenericCacheDTO<UUID, SubLocalityLevel1DTO, UUID, UUID> sL1CacheDTO =
                subLocalityLevel1Service.getSubLocalityLevel1DTOCacheById(sL1Id);
        if (sL1CacheDTO == null) return breadcrumbDTOList;

        GenericCacheDTO<UUID, LocalityDTO, UUID, UUID> localityCacheDTO =
                localityService.getLocalityCacheById(sL1CacheDTO.getParent());

        if (localityCacheDTO == null) return breadcrumbDTOList;

        GenericCacheDTO<UUID, AdministrativeAreaLevel1DTO, UUID, UUID> aL1CacheDTO =
                administrativeAreaLevel1Service.getAdministrativeAreaLevel1DTOCacheById(
                        localityCacheDTO.getParent());
        if (aL1CacheDTO == null) return breadcrumbDTOList;

        GenericCacheDTO<UUID, CountryDTO, UUID, UUID> countryCacheDTO =
                countryService.getCountryCacheById(aL1CacheDTO.getParent());
        if (countryCacheDTO == null) return breadcrumbDTOList;

        BreadcrumbDTO breadcrumbDTO = new BreadcrumbDTO(HOME, false, DIRECTORY);
        breadcrumbDTOList.add(breadcrumbDTO);

        BreadcrumbDTO breadcrumbDTO1 =
                new BreadcrumbDTO(
                        countryCacheDTO.getData().getName(),
                        false,
                        DIRECTORY + "/" + countryCacheDTO.getIdentifier());

        breadcrumbDTOList.add(breadcrumbDTO1);

        BreadcrumbDTO breadcrumbDTO2 =
                new BreadcrumbDTO(
                        localityCacheDTO.getData().getName(),
                        false,
                        DIRECTORY
                                + "/"
                                + countryCacheDTO.getIdentifier()
                                + "/"
                                + localityCacheDTO.getIdentifier());

        breadcrumbDTOList.add(breadcrumbDTO2);

        BreadcrumbDTO breadcrumbDTO3 =
                new BreadcrumbDTO(
                        sL1CacheDTO.getData().getName(),
                        true,
                        DIRECTORY
                                + "/"
                                + countryCacheDTO.getIdentifier()
                                + "/"
                                + localityCacheDTO.getIdentifier()
                                + "/"
                                + sL1CacheDTO.getIdentifier());

        breadcrumbDTOList.add(breadcrumbDTO3);

        return breadcrumbDTOList;
    }
}
