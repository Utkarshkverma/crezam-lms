package com.crezam.lms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(
        name = "Sign up Request",
        description = "Sign up request from the client"
)
public class SignUpRequestDto {
    @NotBlank(message = "User name cannot be blank")
    @Size(min = 3, max = 20, message = "User name can be between 3-20 characters")
    @Schema(
            description = "User name of the client"
    )
    private String userName;
    @NotBlank(message = "Email cannot be blank")
    @Size(max = 50, message = "Email cannot exceed 50 characters")
    @Email(message = "Email is not well formatted")
    @Schema(
            description = "email of the client"
    )
    private String email;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8,message = "Password should be minimum 8 characters long")
    @Schema(description = "Password of the client")
    private String password;
    @NotBlank(message = "Phone no. cannot be blank")
    @Pattern(regexp = "^[789]\\d{9}$", message = "Phone number must be a valid 10-digit number starting with 7, 8, or 9")
    @Schema(description = "Phone number of the client")
    private String phoneNumber;
}
