package com.crezam.lms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        name = "User",
        description = "Schema representing the User entity"
)
public class UserDTO {
    @Schema(
            description = "Schema for id of the user"
    )
    private String userId;
    @Schema(
            description = "Schema name of the user"
    )
    private String userName;
    @Schema(
            description = "Schema for email of the user"
    )
    private String email;
    @Schema(
            description = "Schema to contact no of the user"
    )
    private String contactNumber;
    @Schema(
            description = "Schema for active state of the user"
    )
    private String isActive;
    @Schema(
            description = "Schema for sign up method"
    )
    private String signUpMethod;

}
