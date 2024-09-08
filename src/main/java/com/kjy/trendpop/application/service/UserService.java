package com.kjy.trendpop.application.service;

import com.kjy.trendpop.domain.model.User;
import com.kjy.trendpop.infrastructure.mapper.UserMapper;
import com.kjy.trendpop.presentation.dto.request.UserRequest;
import com.kjy.trendpop.presentation.dto.response.UserResponse;
import com.kjy.trendpop.sequrity.jwt.JwtUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    public UserService(UserMapper userMapper, JwtUtil jwtUtil) {
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
    }

    public UserResponse viewUser(String id) {
        User user = userMapper.find(id);
        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse createUser(User user) {
        // 비밀번호 암호화
        String hashedPassword = BCrypt.hashpw(user.password(), BCrypt.gensalt());

        // 암호화된 비밀번호로 User 객체를 업데이트
        User encryptedUser = new User(user.id(),user.email(), hashedPassword, user.name(), user.nickname(), user.description(),
                user.profile_image_url(),user.phone_number());

        // DB에 User 저장
        userMapper.create(encryptedUser);

        // 저장된 User를 다시 조회
        User foundUser = userMapper.find(encryptedUser.id());

        // UserResponse로 변환하여 반환
        return UserResponse.from(foundUser);
    }

    public String authenticate(UserRequest userRequest) throws Exception {
        // DB에서 사용자 정보 조회
        User user = userMapper.find(userRequest.toDomain().id());

        if (user == null) {
            // 사용자가 존재하지 않으면 예외 발생
            throw new Exception("USER_NOT_FOUND");
        }

        // 비밀번호 검증 (로그인 시 암호화된 비밀번호와 비교)
        if (BCrypt.checkpw(userRequest.toDomain().password(), user.password())) {
            // 비밀번호가 일치하면 JWT 토큰 생성
            return jwtUtil.generateToken(user.id());
        } else {
            // 비밀번호가 틀리면 예외 발생
            throw new Exception("INVALID_CREDENTIALS");
        }

    }
}
