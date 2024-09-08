package com.kjy.trendpop.presentation.dto.request;

import com.example.mybatispractice.domain.model.Person;
import com.kjy.trendpop.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private String id;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String description;
    private String profile_image_url;
    private String phone_number;
    public User toDomain() {
        return new User(id, email, password, name, nickname, description, profile_image_url, phone_number);
    }

}
