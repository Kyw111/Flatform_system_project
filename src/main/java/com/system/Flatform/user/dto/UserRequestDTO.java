package com.system.Flatform.user.dto;

import com.system.Flatform.user.domain.Role;
import com.system.Flatform.user.domain.User;
import com.system.Flatform.utils.PasswordUtil;

import java.util.List;

public record UserRequestDTO (
        String userId,
        String userEmail,
        String password,
        String username,
        String userPhone
){
    public User toEntity(List<Role> roles) {
        return User.builder()
                .userId(userId)
                .userEmail(userEmail)
                .password(PasswordUtil.hashPassword(password))
                .username(username)
                .userPhone(userPhone)
                .roles(roles)
                .build();
    }
}
