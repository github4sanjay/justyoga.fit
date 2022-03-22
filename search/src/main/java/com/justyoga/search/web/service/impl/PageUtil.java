package com.justyoga.search.web.service.impl;

import com.justyoga.util.page.PageDTO;
import org.springframework.data.domain.Page;

public class PageUtil {

    public static <T> PageDTO<T> convert(Page<T> page) {
        return PageDTO.<T>builder()
                .content(page.getContent())
                .first(page.isFirst())
                .hasContent(page.hasContent())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .last(page.isLast())
                .number(page.getNumber())
                .numberOfElements(page.getNumberOfElements())
                .size(page.getSize())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
    }
}
