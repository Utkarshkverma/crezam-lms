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
            description = "Schema to hold the current Page number",
            example = "5"
    )
    private  int pageNumber;
    @Schema(
            description = "Schema to hold the no of entities in current Page",
            example = "10"
    )
    private int pageSize;
    @Schema(
            description = "Schema to hold the total no of entities in our database",
            example = "1000"
    )
    private long totalElements;
    @Schema(
            description = "Schema to hold the total no. of pages formed",
            example = "100"
    )
    private  int totalPages;
    @Schema(
            description = "Schema to tell if the current page is last page",
            example = "true"
    )
    private boolean isLast;
    @Schema(
            description = "Schema to hold the list of content inside the page",
            example = "[\n" +
                    "    {\n" +
                    "        \"isbn\": \"978-3-16-148410-0\",\n" +
                    "        \"title\": \"Effective Java\",\n" +
                    "        \"author\": \"Joshua Bloch\",\n" +
                    "        \"category\": \"Programming\",\n" +
                    "        \"availableCopies\": 10,\n" +
                    "        \"publishedYear\": 2021\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"isbn\": \"978-1-56619-909-4\",\n" +
                    "        \"title\": \"Clean Code\",\n" +
                    "        \"author\": \"Robert C. Martin\",\n" +
                    "        \"category\": \"Programming\",\n" +
                    "        \"availableCopies\": 15,\n" +
                    "        \"publishedYear\": 2008\n" +
                    "    }\n" +
                    "]\n"

    )
    private List<T> content;
}
