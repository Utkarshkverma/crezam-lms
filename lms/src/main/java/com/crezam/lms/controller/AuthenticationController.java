package com.crezam.lms.controller;

import com.crezam.lms.repository.RoleRepository;
import com.crezam.lms.repository.UserRepository;
import com.crezam.lms.dto.*;
import com.crezam.lms.entity.AppRole;
import com.crezam.lms.entity.Role;
import com.crezam.lms.entity.User;
import com.crezam.lms.exception.RoleNotFoundException;
import com.crezam.lms.security.jwt.JwtUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth",produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(
        name = "API endpoints for managing authentication",
        description = "API endpoints for Adding the user, provide facility to log in, check and update his/her credentials "
)
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @PostMapping("/public/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequest) {
        logger.debug("Authenticating user {}", loginRequest);
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        }catch (AuthenticationException exception)
        {
            logger.error("Authentication failed: {}", exception.getMessage());
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        LoginResponseDto loginResponseDto = new LoginResponseDto(userDetails.getUsername(), roles, jwtToken);
        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
    }

    @PostMapping("/public/sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequestDto signUpRequest)
    {
        if(userRepository.existsByEmail(signUpRequest.getEmail()))
        {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto();
            errorResponseDto.setStatus(HttpStatus.BAD_REQUEST);
            errorResponseDto.setErrorTime(LocalDateTime.now());
            errorResponseDto.setApiPath("/auth/public/sign-up");
            errorResponseDto.setErrorMessage("Email already in use");
            return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByUserName(signUpRequest.getUserName()))
        {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto();
            errorResponseDto.setStatus(HttpStatus.BAD_REQUEST);
            errorResponseDto.setErrorTime(LocalDateTime.now());
            errorResponseDto.setApiPath("/auth/public/sign-up");
            errorResponseDto.setErrorMessage("Username already in use");
            return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByContactNumber(signUpRequest.getPhoneNumber()))
        {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto();
            errorResponseDto.setStatus(HttpStatus.BAD_REQUEST);
            errorResponseDto.setErrorTime(LocalDateTime.now());
            errorResponseDto.setApiPath("/auth/public/sign-up");
            errorResponseDto.setErrorMessage("Phone number already in use");
            return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
        }

        Role role;
        if(signUpRequest.getEmail().endsWith("proton.me"))
            role = roleRepository
                    .findByRoleName(AppRole.ROLE_ADMIN)
                    .orElseThrow(()-> new RoleNotFoundException("ROLE_ADMIN"));
        else
            role = roleRepository
                    .findByRoleName(AppRole.ROLE_USER)
                    .orElseThrow(()-> new RoleNotFoundException("ROLE_USER"));

        User user = new User();
        user.setUserName(signUpRequest.getUserName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setContactNumber(signUpRequest.getPhoneNumber());
        user.setRole(role);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
        user.setAccountExpiryDate(LocalDate.now().plusYears(1));
        user.setSignUpMethod("email");
        userRepository.save(user);

        CustomMessageDto customMessageDto = new CustomMessageDto();
        customMessageDto.setMessage("User Registered successfully");
        customMessageDto.setSuccess(true);
        customMessageDto.setHttpStatus(HttpStatus.OK);

        return new ResponseEntity<>(customMessageDto, HttpStatus.OK);

    }

    @GetMapping("/username")
    public String currentUserName(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository
                .findByEmail(userDetails.getUsername())
                .orElseThrow(()-> new RuntimeException("Error: User is not found."));

        return user.getUserName();
    }


}
