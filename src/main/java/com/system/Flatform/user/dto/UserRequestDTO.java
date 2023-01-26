package com.system.Flatform.user.dto;

import com.system.Flatform.user.domain.User;

public record UserRequestDTO (
        String userId,
        String userEmail,
        String password,
        String username,
        String userPhone
){
    public User toEntity() {
        return User.builder()
                .userId(userId)
                .userEmail(userEmail)
//                .password()
                .username(username)
                .userPhone(userPhone)
                .build();
    }
}
