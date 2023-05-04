//package com.example.seatimecalculator2.config;
//
//import com.example.seatimecalculator2.repository.UserRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//@Configuration
//@AllArgsConstructor
//public class UserAppConfig {
//
//    private final UserRepository userRepository;
//
//    @Bean
//    public UserDetailsService userDetailsService(){
//        return username -> (UserDetails) userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
//    }
//}
