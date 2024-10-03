package com.trendpop.application.service;

import com.trendpop.domain.model.User;
import com.trendpop.exception.InvalidCredentialsException;
import com.trendpop.exception.UserNotFoundException;
import com.trendpop.infrastructure.mapper.UserMapper;
import com.trendpop.presentation.dto.request.UserRequest;
import com.trendpop.presentation.dto.response.UserResponse;
import com.trendpop.domain.model.encryption.PasswordEncryptor;
import com.trendpop.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    public UserResponse viewUser(String id) {
        User user = userMapper.findUserById(id);
        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse createUser(User user) {
        String hashedPassword = PasswordEncryptor.encrypt(user.password());

        User encryptedUser = new User(user.id(),user.email(), hashedPassword, user.name(), user.nickname(), user.description(),
                user.profile_image_url(),user.phone_number());

        userMapper.createUser(encryptedUser);

        User foundUser = userMapper.findUserById(encryptedUser.id());

        return UserResponse.from(foundUser);
    }

    public String login(UserRequest userRequest) {
        User user = userMapper.findUserById(userRequest.toDomain().id());

        if (user == null) {
            throw new UserNotFoundException();
        }

        if (PasswordEncryptor.checkPassword(userRequest.toDomain().password(), user.password())) {
            return jwtUtil.generateToken(user.id());
        } else {
            throw new InvalidCredentialsException();
        }

    }
}
