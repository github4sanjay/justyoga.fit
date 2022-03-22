package com.justyoga.profile.web.service.impl;

import com.justyoga.client.library.MediaClient;
import com.justyoga.profile.cache.BucketVsUrl;
import com.justyoga.profile.domain.model.mysql.Video;
import com.justyoga.profile.service.interfaces.VideoService;
import com.justyoga.profile.web.config.WebConfig;
import com.justyoga.profile.web.service.interfaces.VideoUiService;
import com.justyoga.util.dto.profile.VideoDTO;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import com.justyoga.util.page.PageDTO;
import com.justyoga.util.response.BaseResponse;
import com.justyoga.util.response.Status;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VideoUiServiceImpl implements VideoUiService {

    private final MediaClient mediaClient;
    private final VideoService videoService;
    private final BucketVsUrl bucketVsUrl;
    private final ModelMapper modelMapper;

    @Autowired
    public VideoUiServiceImpl(
            MediaClient mediaClient,
            VideoService videoService,
            BucketVsUrl bucketVsUrl,
            @Qualifier(WebConfig.MODEL_MAPPER) ModelMapper modelMapper) {
        this.mediaClient = mediaClient;
        this.videoService = videoService;
        this.bucketVsUrl = bucketVsUrl;
        this.modelMapper = modelMapper;
    }

    public Video convert(VideoDTO dto) {
        if (dto.getId() != null) {
            var video = videoService.findById(dto.getId());
            if (video != null) {
                video.setDescription(dto.getDescription());
                video.setTitle(dto.getTitle());
                video.setDuration(dto.getDuration());
                return video;
            }
        }
        return modelMapper.map(dto, Video.class);
    }

    public VideoDTO convert(Video entity) {
        var dto = modelMapper.map(entity, VideoDTO.class);
        dto.setUrl(getUrl(dto.getUrl()));
        dto.setCoverPic(getUrl(dto.getCoverPic()));
        return dto;
    }

    @Override
    public VideoDTO save(UUID userId, VideoDTO videoDTO) {
        if (!userId.equals(videoDTO.getUserId())) {
            throw new AppException("Not authorized", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        return convert(videoService.save(convert(videoDTO)));
    }

    @Override
    public boolean deleteMedia(UUID id, UUID userID) {
        var imageDTO = videoService.findById(id);
        if (imageDTO == null)
            throw new AppException("Media does not exist", AppStatusCode.NOT_FOUND);
        if (!userID.equals(imageDTO.getUserId()))
            throw new AppException(
                    "Not allowed for this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        return videoService.delete(id);
    }

    @Override
    public List<VideoDTO> findByUserId(UUID userId) {
        return videoService.findByUserId(userId).stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public PageDTO<VideoDTO> find(Integer page, Integer count, String sort, UUID userId) {
        if (page == null) page = 0;
        if (count == null) count = 10;
        Page<Video> videos;
        if (userId != null) {
            videos = videoService.findAllByUserId(page, count, sort, userId);
        } else {
            videos = videoService.findAll(page, count, sort);
        }

        return PageDTO.<VideoDTO>builder()
                .content(
                        videos.getContent().stream()
                                .map(this::convert)
                                .collect(Collectors.toList()))
                .first(videos.isFirst())
                .hasContent(videos.hasContent())
                .hasNext(videos.hasNext())
                .hasPrevious(videos.hasPrevious())
                .last(videos.isLast())
                .number(videos.getNumber())
                .numberOfElements(videos.getNumberOfElements())
                .size(videos.getSize())
                .totalElements(videos.getTotalElements())
                .totalPages(videos.getTotalPages())
                .build();
    }

    @Override
    public VideoDTO findById(UUID id) {
        Video videoDTO = videoService.findById(id);
        if (videoDTO == null) {
            throw new AppException("Video not found", AppStatusCode.NOT_FOUND);
        }
        return convert(videoDTO);
    }

    @Override
    public VideoDTO update(VideoDTO dto, UUID userId) {
        if (!userId.equals(dto.getUserId())) {
            throw new AppException("Not authorized", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        return convert(videoService.save(convert(dto)));
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
