package com.example.seatimecalculator2.controller.REST;

import com.example.seatimecalculator2.entity.user.User;
import com.example.seatimecalculator2.service.activationToken.AccountTokenService;
import com.example.seatimecalculator2.service.authentificatedUser.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ResetPasswordREST {
    private final UserService userService;
    private final AccountTokenService tokenService;
    @Value("${web.site.link.password_reset}")
    String link;

    @PostMapping("/resetPassword")
    public boolean resetPassword(@RequestBody String email) {
        User user = null;
        try {
            user = userService.findUserByEmail(email);
        } catch (NoSuchElementException e) {
            return false;
        }
        if (user != null) {
            tokenService.sendActivationCode(user, link);
            return true;
        } else {
            return false;
        }
    }
}
