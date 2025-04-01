package com.tekworks.auth_service.service;


import com.tekworks.auth_service.entity.UserCerdencials;
import com.tekworks.auth_service.repository.UserCerdencialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
    private  UserCerdencialsRepository userCerdencialsRepository;

  
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCerdencials user = userCerdencialsRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(user);
    }
}

