//package com.tekworks.microservice.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@EnableWebSecurity
//@Configuration
//@EnableMethodSecurity(prePostEnabled = true)
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeHttpRequests(authz -> authz
//                .requestMatchers("/actuator/health").permitAll()
//                .anyRequest().authenticated()
//            )
//            .oauth2ResourceServer(server -> server.jwt());
//
//        return http.build();
//    }
//    
//}
