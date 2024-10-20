package com.crezam.lms.service.impl;

import com.crezam.lms.Repository.RoleRepository;
import com.crezam.lms.Repository.UserRepository;
import com.crezam.lms.dto.CustomMessageDto;
import com.crezam.lms.dto.CustomPageResponseDto;
import com.crezam.lms.dto.UserDTO;
import com.crezam.lms.entity.AppRole;
import com.crezam.lms.entity.Role;
import com.crezam.lms.entity.User;
import com.crezam.lms.exception.RoleNotFoundException;
import com.crezam.lms.exception.UserNotFoundException;
import com.crezam.lms.mapper.UserMapper;
import com.crezam.lms.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public CustomMessageDto updateUserRole(String userId, String roleName) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new UserNotFoundException(userId));
        AppRole appRole = AppRole.valueOf(roleName);
        Role role = roleRepository.findByRoleName(appRole)
                .orElseThrow(() -> new RoleNotFoundException(roleName));
        user.setRole(role);
        userRepository.save(user);

        return CustomMessageDto
                .builder()
                .message("Role has been updated")
                .success(true)
                .httpStatus(HttpStatus.NO_CONTENT)
                .build();
    }

    @Override
    public CustomPageResponseDto<Object> getAllUsers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<User> users = userRepository.findAll(pageable);
        List<UserDTO> userDTOs = users.stream().map(userMapper::entityToDto).toList();

        return CustomPageResponseDto
                .builder()
                .pageNumber(users.getNumber())
                .pageSize(users.getSize())
                .isLast(users.isLast())
                .totalElements(users.getTotalElements())
                .totalPages(users.getTotalPages())
                .content(Collections.singletonList(userDTOs))
                .build();
    }



    @Override
    public UserDTO getUser(String userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return userMapper.entityToDto(user);
    }

    @Override
    public CustomMessageDto deleteUser(String userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete,()->{
            throw new UserNotFoundException(userId);
        });
        return CustomMessageDto
                .builder()
                .message("User has been removed successfully")
                .success(true)
                .httpStatus(HttpStatus.NO_CONTENT)
                .build();
    }
}
