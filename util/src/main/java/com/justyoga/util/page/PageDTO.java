package com.justyoga.util.page;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageDTO<T> {
    Integer number;
    Integer size;
    Integer numberOfElements;
    List<T> content;
    Boolean hasContent;
    Boolean first;
    Boolean last;
    Boolean hasNext;
    Boolean hasPrevious;
    Long totalElements;
    Integer totalPages;
}
