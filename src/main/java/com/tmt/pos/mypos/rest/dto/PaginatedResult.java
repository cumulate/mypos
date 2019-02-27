package com.tmt.pos.mypos.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;
@Data
@ToString(exclude = {"content"})
@Builder
@AllArgsConstructor
public class PaginatedResult<T> {
    private List<T> content;
    private long totalPages;
    private long totalElements;
    private long size;
    private boolean isFirst ;
    private boolean isLast ;
}
