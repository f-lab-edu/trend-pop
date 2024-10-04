package com.trendpop.presentation.controller;

import com.trendpop.application.service.UserService;
import com.trendpop.presentation.dto.request.UserRequest;
import com.trendpop.presentation.dto.response.UserResponse;
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

    @PostMapping("/login")
    public String createAuthenticationToken(@RequestBody UserRequest userRequest) throws Exception {
        return userService.login(userRequest);
    }
}
