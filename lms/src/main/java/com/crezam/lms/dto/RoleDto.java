package com.crezam.lms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "Role",
        description = "Schema used for updating the role of the user"
)
public class RoleDto {
    @Schema(
            description = "Role provided by Admin \n Either it can be ROLE_ADMIN or ROLE_USER"
    )
    private String roleName;
}
