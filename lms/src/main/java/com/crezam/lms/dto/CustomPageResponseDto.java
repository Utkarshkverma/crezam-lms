package com.crezam.lms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
        name = "Custom Page Response",
        description = "Schema to handle the pagination"
)
public class CustomPageResponseDto<T>
{
    @Schema(
            description = "Schema to hold the current Page number"
    )
    private  int pageNumber;
    @Schema(
            description = "Schema to hold the no of entities in current Page"
    )
    private int pageSize;
    @Schema(
            description = "Schema to hold the total no of entities in our database"
    )
    private long totalElements;
    @Schema(
            description = "Schema to hold the total no. of pages formed"
    )
    private  int totalPages;
    @Schema(
            description = "Schema to tell if the current page is last page"
    )
    private boolean isLast;
    @Schema(
            description = "Schema to hold the list of content inside the page"

    )
    private List<T> content;
}
