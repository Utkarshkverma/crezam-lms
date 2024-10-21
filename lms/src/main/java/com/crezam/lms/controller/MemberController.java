package com.crezam.lms.controller;

import com.crezam.lms.constants.AppConstant;
import com.crezam.lms.dto.*;
import com.crezam.lms.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "API endpoints for managing members",
        description = "API endpoints for managing the user, provide facility to find members, update roles, delete the members"
)
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/members",produces = {MediaType.APPLICATION_JSON_VALUE})
public class MemberController {

    private final IUserService userService;

    @Operation(
            summary = "fetch all users (Admin only)",
            description = "This endpoint will allow admin to fetch all users"

    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "302",
                    content = @Content(
                            schema = @Schema(implementation = CustomPageResponseDto.class)
                    )

            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<CustomPageResponseDto<Object>> findAll(
            @Parameter(description = "Page number",required = false,example = "0")
            @RequestParam(name = "page",defaultValue = AppConstant.PAGE_NO,required = false) int page,
            @Parameter(description = "No of entities per page",required = false,example = "0")
            @RequestParam(name = "size",defaultValue = AppConstant.PAGE_SIZE,required = false) int size
    ){
        return  ResponseEntity
                .status(HttpStatus.FOUND)
                .body(userService.getAllUsers(page,size));
    }


    @Operation(
            summary = "Find the member by Id (ADMIN only)",
            description = "This endpoint will allow you to find the member by the id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "302",
                    description = "User found successfully",
                    content = @Content(
                            schema = @Schema(implementation = UserDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getMember(
            @Parameter(description = "The id of the member to retrieve (ADMIN only)", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable String id){
        UserDTO userDto = userService.getUser(id);
        return new ResponseEntity<>(userDto,HttpStatus.FOUND);
    }
    @Operation(
            summary = "Update the role of member by Id (ADMIN only)",
            description = "This endpoint will allow to update the role of a member by fetching from id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "302",
                    description = "User found successfully",
                    content = @Content(
                            schema = @Schema(implementation = CustomMessageDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User or Role not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PutMapping("/{id}")
    public ResponseEntity<CustomMessageDto> updateUserRole(
            @Parameter(description = "The id of the member to update (ADMIN only)", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable String id,
            @RequestBody RoleDto roleDto

    )
    {
        String role = roleDto.getRoleName();
        CustomMessageDto customMessageDto = userService.updateUserRole(id, role);
        return new ResponseEntity<>(customMessageDto,HttpStatus.OK);
    }
    @Operation(
            summary = "Delete the role of member by Id (ADMIN only)",
            description = "This endpoint will allow to delete a member"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "302",
                    description = "User deleted successfully",
                    content = @Content(
                            schema = @Schema(implementation = CustomMessageDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomMessageDto> deleteMember(
            @Parameter(description = "The id of the member to delete (ADMIN only)", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable String id
    )
    {
        CustomMessageDto customMessageDto = userService.deleteUser(id);
        return new ResponseEntity<>(customMessageDto,HttpStatus.NO_CONTENT);
    }


}
