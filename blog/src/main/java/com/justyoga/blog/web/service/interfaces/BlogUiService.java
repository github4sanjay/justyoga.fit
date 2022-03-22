package com.justyoga.blog.web.service.interfaces;

import com.justyoga.util.dto.blog.BlogDTO;
import com.justyoga.util.page.PageDTO;
import java.util.List;
import java.util.UUID;

public interface BlogUiService {

    BlogDTO save(BlogDTO blogDTO, UUID currentUser);

    BlogDTO findById(UUID id);

    List<BlogDTO> findByUserId(UUID id);

    PageDTO<BlogDTO> find(Integer page, Integer count, String sort, UUID userId);

    Boolean delete(UUID id, UUID currentUser);

    BlogDTO saveCover(UUID blogId, String url, UUID userId);
}
