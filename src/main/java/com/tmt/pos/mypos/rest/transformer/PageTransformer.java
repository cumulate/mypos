package com.tmt.pos.mypos.rest.transformer;

import com.tmt.pos.mypos.rest.dto.PaginatedResult;
import org.springframework.data.domain.Page;

/**
 * Transforms the pageable Entity to rest friendly Json DTO binding the pagination information
 */
public class PageTransformer {

     public static <T> PaginatedResult<T> transformOutput(Page<T> page){
        return  PaginatedResult.<T>builder()
                .content(page.getContent())
                .isFirst(page.isFirst())
                .isLast(page.isLast())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .size(page.getSize())
                .build();

    }
}
