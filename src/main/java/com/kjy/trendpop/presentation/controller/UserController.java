package com.kjy.trendpop.presentation.controller;

import com.example.mybatispractice.application.service.PersonService;
import com.example.mybatispractice.presentation.dto.request.PersonRequest;
import com.example.mybatispractice.presentation.dto.response.PersonResponse;
import com.kjy.trendpop.application.service.UserService;
import com.kjy.trendpop.presentation.dto.request.UserRequest;
import com.kjy.trendpop.presentation.dto.response.UserResponse;
import com.kjy.trendpop.sequrity.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserResponse viewUser(@PathVariable("id") String id) {
        return userService.viewUser(id);
    }

    @PostMapping("/signup")
    public UserResponse registerUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest.toDomain());
    }

    @PostMapping("/authenticate")
    public String createAuthenticationToken(@RequestBody UserRequest userRequest) throws Exception {
        // UserService에서 인증 로직 처리
        return userService.authenticate(userRequest);
    }
}
