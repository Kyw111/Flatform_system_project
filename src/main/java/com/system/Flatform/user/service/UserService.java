package com.system.Flatform.user.service;

import com.system.Flatform.user.domain.User;
import com.system.Flatform.user.dto.UserRequestDTO;

public interface UserService {
    User signUp (UserRequestDTO userRequestDTO) throws Exception;
}
