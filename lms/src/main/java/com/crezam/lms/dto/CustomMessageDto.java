package com.crezam.lms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        name = "Custom Message",
        description = "Schema responsible for custom message response"
)
public class CustomMessageDto {
    @Schema(
            description = "Schema provided by server"
    )
    private String message;
    @Schema(
            description = "Message to send success flag"
    )
    private boolean success;
    @Schema(
            description = "Schema to send http status"
    )
    private HttpStatus httpStatus;

}
