package com.justyoga.place.web.service.interfaces;

import com.justyoga.util.dto.place.BreadcrumbDTO;
import java.util.List;
import java.util.UUID;

public interface BreadcrumbService {
    List<BreadcrumbDTO> getBreadcrumbsForLocalityByCountryId(UUID countryId);

    List<BreadcrumbDTO> getBreadcrumbsForLocalityId(UUID localityId);

    List<BreadcrumbDTO> getBreadcrumbsForSL1Id(UUID sL1Id);
}
