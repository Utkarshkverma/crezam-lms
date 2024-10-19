package com.crezam.lms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "Error Response",
        description = "Schema to hold error response information"
)
public class ErrorResponseDto {
    @Schema(
            description = "API path invoked by the client",
            example = "/api/v1/books/978-1-4028-9462-6"
    )
    private String apiPath;
    @Schema(
            description = "Error code representing the error happened",
            example = "BAD_REQUEST"
    )
    private HttpStatus status;
    @Schema(
            description = "Error message representing the error happened",
            example = "Book not found with isbn: 978-1-4028-9462-6"
    )
    private String errorMessage;
    @Schema(
            description = "Time representing when the error happened",
            example = "2024-09-12T19:19:48.4453257"
    )
    private LocalDateTime errorTime;

}

