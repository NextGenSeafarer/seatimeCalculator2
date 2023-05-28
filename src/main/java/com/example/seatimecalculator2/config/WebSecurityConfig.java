package com.example.seatimecalculator2.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

//    private final Filter jwtAuthFilter;
//    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http
                .cors().and().csrf().disable() // TODO : read about csrf and cors to be able to lock certain post methods
                .authorizeHttpRequests((requests) -> {
                            try {
                                requests
                                        .requestMatchers(
                                                "/",
                                                "/about",
                                                "/contacts",
                                                "/registration",
                                                "/account/activation/*",
                                                "/static/**").permitAll()
                                        .anyRequest().authenticated();
//                                        .and()
//                                        .sessionManagement()
//                                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//                                        .and()
//                                        .authenticationProvider(authenticationProvider);
//                                        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout().logoutSuccessUrl("/").permitAll();
        return http.build();
    }
}

