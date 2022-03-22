package com.justyoga.profile.web.service.impl;

import com.justyoga.profile.domain.model.mysql.UserBasicInfo;
import com.justyoga.profile.service.interfaces.UserBasicInfoService;
import com.justyoga.profile.web.service.interfaces.UserBasicInfoUiService;
import com.justyoga.util.dto.profile.UserBasicInfoDTO;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import com.justyoga.util.page.PageDTO;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class UserBasicInfoUiServiceImpl implements UserBasicInfoUiService {

    private final UserBasicInfoService userBasicInfoService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserBasicInfoUiServiceImpl(
            UserBasicInfoService userBasicInfoService, ModelMapper modelMapper) {
        this.userBasicInfoService = userBasicInfoService;
        this.modelMapper = modelMapper;
    }

    public UserBasicInfo convert(UserBasicInfoDTO dto) {
        if (dto.getId() != null) {
            var userBasicInfo = userBasicInfoService.findById(dto.getId());
            if (userBasicInfo != null) {
                userBasicInfo.setExperienceMonths(dto.getExperienceMonths());
                userBasicInfo.setAge(dto.getAge());
                userBasicInfo.setExperienceYears(dto.getExperienceYears());
                userBasicInfo.setAdministrativeAreaLevel1Id(dto.getAdministrativeAreaLevel1Id());
                userBasicInfo.setFormattedAddress(dto.getFormattedAddress());
                userBasicInfo.setCountryId(dto.getCountryId());
                userBasicInfo.setLatitude(dto.getLatitude());
                userBasicInfo.setLocalityId(dto.getLocalityId());
                userBasicInfo.setLongitude(dto.getLongitude());
                userBasicInfo.setSubLocalityLevel1Id(dto.getSubLocalityLevel1Id());
                userBasicInfo.setSubLocalityLevel2Id(dto.getSubLocalityLevel2Id());
                userBasicInfo.setGeohash1(dto.getGeohash1());
                userBasicInfo.setGeohash5(dto.getGeohash5());
                userBasicInfo.setGeohash50(dto.getGeohash50());
                userBasicInfo.setGeohash150(dto.getGeohash150());
                userBasicInfo.setPostalCode(dto.getPostalCode());
                return userBasicInfo;
            }
        } else {
            dto.setActive(true);
        }
        return modelMapper.map(dto, UserBasicInfo.class);
    }

    public UserBasicInfoDTO convert(UserBasicInfo userBasicInfo) {
        return modelMapper.map(userBasicInfo, UserBasicInfoDTO.class);
    }

    @Override
    public UserBasicInfoDTO findUserBasicInfoById(UUID id) {
        var userBasicInfo = userBasicInfoService.findById(id);
        if (userBasicInfo == null) return null;
        else return convert(userBasicInfo);
    }

    @Override
    public UserBasicInfoDTO findByUserId(UUID id) {
        var userBasicInfo = userBasicInfoService.findByUserId(id);
        if (userBasicInfo == null) return null;
        else return convert(userBasicInfo);
    }

    @Override
    public UserBasicInfoDTO saveOrUpdate(UserBasicInfoDTO dto, UUID userId) {
        if (dto != null && !userId.equals(dto.getUserId())) {
            throw new AppException("User not allowed", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        if (dto.getId() == null) {
            UserBasicInfo byUserId = userBasicInfoService.findByUserId(dto.getUserId());
            if (byUserId != null)
                throw new AppException("Profile already exist", AppStatusCode.INVALID_REQUEST);
        }
        var save = userBasicInfoService.save(convert(dto));
        return convert(save);
    }

    @Override
    public PageDTO<UserBasicInfoDTO> find(
            Integer page,
            Integer count,
            String sort,
            UUID countryId,
            UUID administrativeAreaLevel1Id,
            UUID localityId,
            UUID subLocalityLevel1Id,
            UUID subLocalityLevel2Id) {

        if (page == null) page = 0;
        if (count == null) count = 10;

        Page<UserBasicInfo> userBasicInfos;
        if (subLocalityLevel2Id != null) {
            userBasicInfos =
                    userBasicInfoService.findAllBySubLocalityLevel2Id(
                            subLocalityLevel2Id, page, count, sort);
        } else if (subLocalityLevel1Id != null) {
            userBasicInfos =
                    userBasicInfoService.findAllBySubLocalityLevel1Id(
                            subLocalityLevel1Id, page, count, sort);
        } else if (localityId != null) {
            userBasicInfos =
                    userBasicInfoService.findAllByLocalityId(localityId, page, count, sort);
        } else if (administrativeAreaLevel1Id != null) {
            userBasicInfos =
                    userBasicInfoService.findAllByAdministrativeAreaLevel1Id(
                            administrativeAreaLevel1Id, page, count, sort);
        } else if (countryId != null) {
            userBasicInfos = userBasicInfoService.findAllByCountryId(countryId, page, count, sort);
        } else {
            userBasicInfos = userBasicInfoService.findAll(page, count, sort);
        }
        return PageDTO.<UserBasicInfoDTO>builder()
                .content(
                        userBasicInfos.getContent().stream()
                                .map(this::convert)
                                .collect(Collectors.toList()))
                .first(userBasicInfos.isFirst())
                .hasContent(userBasicInfos.hasContent())
                .hasNext(userBasicInfos.hasNext())
                .hasPrevious(userBasicInfos.hasPrevious())
                .last(userBasicInfos.isLast())
                .number(userBasicInfos.getNumber())
                .numberOfElements(userBasicInfos.getNumberOfElements())
                .size(userBasicInfos.getSize())
                .size(userBasicInfos.getSize())
                .totalPages(userBasicInfos.getTotalPages())
                .totalElements(userBasicInfos.getTotalElements())
                .build();
    }
}
