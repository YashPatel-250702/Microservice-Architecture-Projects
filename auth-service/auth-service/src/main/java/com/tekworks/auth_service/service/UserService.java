package com.tekworks.auth_service.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tekworks.auth_service.dto.LoginRequest;
import com.tekworks.auth_service.dto.UserCerdencialRequest;
import com.tekworks.auth_service.entity.UserCerdencials;
import com.tekworks.auth_service.repository.UserCerdencialsRepository;

@Service
public class UserService  {

    @Autowired
    private UserCerdencialsRepository userCerdencialsRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JWTService jwtService ;


  
    public int saveUser(UserCerdencialRequest userCerdencialRequest) {

        UserCerdencials userCerdencials = modelMapper.map(userCerdencialRequest, UserCerdencials.class);
        userCerdencials.setPassword(passwordEncoder.encode(userCerdencials.getPassword()));
        userCerdencials = userCerdencialsRepo.save(userCerdencials);
        return userCerdencials.getUserId();

    }

  
    public String login(LoginRequest loginRequest) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        if (authentication.isAuthenticated()) {

            return jwtService.generateToken(loginRequest.getUsername());
        }
        return "Login Failed";
    }
}
