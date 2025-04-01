package com.tekworks.auth_service.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tekworks.auth_service.dto.LoginRequest;
import com.tekworks.auth_service.dto.UserCerdencialRequest;
import com.tekworks.auth_service.service.UserService;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/saveUser")
    public ResponseEntity<Integer> saveUser(@RequestBody UserCerdencialRequest userCerdencialRequest) {

        int userId = userService.saveUser(userCerdencialRequest);
        logger.info("User Id: {}", userId);
        return ResponseEntity.ok(userId);

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {

        String token = userService.login(loginRequest);
        logger.info("Token: {}", token);
        return ResponseEntity.ok(token);
    }
}
