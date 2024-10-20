package com.crezam.lms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Schema(
        name = "Login Response",
        description = "Schema for login response"
)
public class LoginResponseDto {
    @Schema(
            description = "Schema for Jwt token received by server"
    )
    private String jwtToken;
    @Schema(
            description = "Schema to get user name"
    )
    private String username;
    @Schema(
            description = "Schema for the roles assigned by server"
    )
    private List<String> roles;

    public LoginResponseDto(String username, List<String> roles, String jwtToken) {
        this.username = username;
        this.roles = roles;
        this.jwtToken = jwtToken;
    }


}
