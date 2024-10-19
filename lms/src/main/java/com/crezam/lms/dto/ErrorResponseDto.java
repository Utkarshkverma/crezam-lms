package com.crezam.lms.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponseDto {

    private String apiPath;
    private String message;
    private HttpStatus httpStatus;
    private LocalDateTime timestamp;
}
