package com.crezam.lms.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomPageResponseDto<T>
{
    private  int pageNumber;
    private int pageSize;
    private long totalElements;
    private  int totalPages;
    private boolean isLast;
    private List<T> content;
}
