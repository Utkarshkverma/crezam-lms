package com.crezam.lms.service;

import com.crezam.lms.dto.CustomMessageDto;
import com.crezam.lms.dto.CustomPageResponseDto;
import com.crezam.lms.dto.UserDTO;

import java.util.List;

public interface IUserService {
    CustomMessageDto updateUserRole(String userId, String roleName);
    CustomPageResponseDto<Object> getAllUsers(int pageNo, int pageSize);
    UserDTO getUser(String userId);
    CustomMessageDto deleteUser(String userId);
}
