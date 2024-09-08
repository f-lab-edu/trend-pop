package com.kjy.trendpop.domain.model;

public record User(String id, String email, String password, String name, String nickname, String description,
                   String profile_image_url, String phone_number) {

}
