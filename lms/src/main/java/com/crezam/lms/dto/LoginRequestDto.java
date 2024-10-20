package com.crezam.lms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Schema(
        name = "Login Request",
        description = "Schema for the login request"
)
public class LoginRequestDto {

    @Email(message = "Email is not well formatted")
    @NotEmpty(message = "Email cannot be empty")
    @NotBlank(message = "Email cannot be blank")
    @Schema(
            description = "Schema to send the email id to the server for authentication"
    )
    private String email;
    @NotEmpty(message = "Password cannot be empty")
    @NotBlank(message = "Password cannot be blank")
    @Schema(
            description = "Schema to send password to the server for authentication"
    )
    private String password;
}
