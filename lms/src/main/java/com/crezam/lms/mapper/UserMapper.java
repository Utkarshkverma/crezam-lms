package com.crezam.lms.mapper;

import com.crezam.lms.dto.UserDTO;
import com.crezam.lms.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO entityToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUserName(user.getUserName());
        userDTO.setEmail(user.getEmail());
        userDTO.setContactNumber(user.getContactNumber());
        userDTO.setIsActive(user.isEnabled()?"ACTIVE":"INACTIVE");
        userDTO.setSignUpMethod(user.getSignUpMethod());
        return userDTO;
    }
}
