package com.system.Flatform.user.service.impl;

import com.system.Flatform.user.domain.Role;
import com.system.Flatform.user.domain.User;
import com.system.Flatform.user.dto.UserRequestDTO;
import com.system.Flatform.user.repository.RoleRepository;
import com.system.Flatform.user.repository.UserRepository;
import com.system.Flatform.user.service.UserService;
import com.system.Flatform.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public User signUp (UserRequestDTO userRequestDTO) throws Exception {
        if (this.isEmailExist(userRequestDTO.userEmail())) {
            throw new Exception("Your mail already exist!!!");
        }

        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByRole(Constants.ROLE_USER));
        User user = userRequestDTO.toEntity(roles);

        return userRepository.save(user);
    }

    private boolean isEmailExist (String email) {
        Optional<User> user = userRepository.findByUserEmail(email);
        return !user.isEmpty();
    }
}
