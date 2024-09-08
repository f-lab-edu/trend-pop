package com.kjy.trendpop.presentation.dto.response;

import com.example.mybatispractice.domain.model.Person;
import com.kjy.trendpop.domain.model.User;

public record UserResponse(String id, String email, String password, String name, String nickname, String description,
                           String profile_image_url, String phone_number) {

    public static UserResponse from(User user) {
        return new UserResponse(user.id(), user.email(), user.password(), user.name(), user.nickname(), user.description(),
                user.profile_image_url(), user.phone_number());
    }
}
