package com.example.authenticationservice.service;

import com.example.authenticationservice.dto.RegisterRequest;
import com.example.authenticationservice.dto.UserDto;
import com.example.authenticationservice.entity.UserEntity;
import com.example.authenticationservice.entity.UserRole;
import com.example.authenticationservice.mapper.UserMapper;
import com.example.authenticationservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final UserValidator userValidator;

    public Long register(RegisterRequest registerRequest) {
        UserEntity user = UserEntity.builder()
                .username(registerRequest.username())
                .password(encoder.encode(registerRequest.password()))
                .userRole(UserRole.USER)
                .build();


        String errs = userValidator.validate(user);
        if (!errs.isEmpty()) {
            throw new InvalidUserException(errs);
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistException();
        }
        return userRepository.save(user).getId();
    }


}
