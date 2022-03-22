package com.justyoga.profile.web.service.impl;

import com.justyoga.client.library.MediaClient;
import com.justyoga.profile.cache.BucketVsUrl;
import com.justyoga.profile.domain.model.mysql.Image;
import com.justyoga.profile.service.interfaces.ImageService;
import com.justyoga.profile.web.config.WebConfig;
import com.justyoga.profile.web.service.interfaces.ImageUiService;
import com.justyoga.util.dto.profile.ImageDTO;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import com.justyoga.util.page.PageDTO;
import com.justyoga.util.response.BaseResponse;
import com.justyoga.util.response.Status;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ImageUiServiceImpl implements ImageUiService {

    private final MediaClient mediaClient;
    private final ImageService imageService;
    private final BucketVsUrl bucketVsUrl;
    private final ModelMapper modelMapper;

    @Autowired
    public ImageUiServiceImpl(
            MediaClient mediaClient,
            ImageService imageService,
            BucketVsUrl bucketVsUrl,
            @Qualifier(WebConfig.MODEL_MAPPER) ModelMapper modelMapper) {
        this.mediaClient = mediaClient;
        this.imageService = imageService;
        this.bucketVsUrl = bucketVsUrl;
        this.modelMapper = modelMapper;
    }

    public Image convert(ImageDTO dto) {
        if (dto.getId() != null) {
            var image = imageService.findById(dto.getId());
            if (image != null) {
                image.setDescription(dto.getDescription());
                image.setTitle(dto.getTitle());
                return image;
            }
        }
        return modelMapper.map(dto, Image.class);
    }

    public ImageDTO convert(Image entity) {
        var dto = modelMapper.map(entity, ImageDTO.class);
        dto.setUrl(getUrl(dto.getUrl()));
        return dto;
    }

    @Override
    public ImageDTO save(UUID userId, ImageDTO imageDTO) {
        if (!userId.equals(imageDTO.getUserId())) {
            throw new AppException("Not authorized", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        var save = imageService.save(convert(imageDTO));
        return convert(save);
    }

    @Override
    public boolean delete(UUID id, UUID userID) {
        Image image = imageService.findById(id);
        if (image == null)
            throw new AppException("Media does not exist", AppStatusCode.INVALID_REQUEST);
        if (!userID.equals(image.getUserId()))
            throw new AppException(
                    "Not allowed for this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        return imageService.delete(id);
    }

    @Override
    public PageDTO<ImageDTO> find(Integer page, Integer count, String sort, UUID userId) {
        if (page == null) page = 0;
        if (count == null) count = 10;
        Page<Image> images;
        if (userId != null) {
            images = imageService.findAllByUserId(page, count, sort, userId);
        } else {
            images = imageService.findAll(page, count, sort);
        }

        return PageDTO.<ImageDTO>builder()
                .content(
                        images.getContent().stream()
                                .map(this::convert)
                                .collect(Collectors.toList()))
                .first(images.isFirst())
                .hasContent(images.hasContent())
                .hasNext(images.hasNext())
                .hasPrevious(images.hasPrevious())
                .last(images.isLast())
                .number(images.getNumber())
                .numberOfElements(images.getNumberOfElements())
                .size(images.getSize())
                .totalElements(images.getTotalElements())
                .totalPages(images.getTotalPages())
                .build();
    }

    @Override
    public ImageDTO findById(UUID id) {
        Image image = imageService.findById(id);
        if (image == null) {
            throw new AppException("Image not found", AppStatusCode.NOT_FOUND);
        }
        return convert(image);
    }

    @Override
    public ImageDTO update(ImageDTO dto, UUID userId) {
        if (!userId.equals(dto.getUserId())) {
            throw new AppException("Not authorized", AppStatusCode.UNAUTHORIZED_ERROR);
        }

        var save = imageService.save(convert(dto));
        return convert(save);
    }

    private String getUrl(String bucketName) {
        if (bucketName == null) return null;
        String url = bucketVsUrl.get(bucketName);
        if (url == null) {
            ResponseEntity<BaseResponse<String>> coverUrlResponse =
                    mediaClient.getUrl(bucketName, 1L, TimeUnit.DAYS);
            if (coverUrlResponse.getStatusCode() == HttpStatus.OK
                    && coverUrlResponse.getBody() != null
                    && coverUrlResponse.getBody().getStatus() == Status.SUCCESS) {
                url = coverUrlResponse.getBody().getData();
                bucketVsUrl.put(bucketName, url);
            }
        }
        return url;
    }
}
