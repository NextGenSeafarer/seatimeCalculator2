package com.example.seatimecalculator2.controller.REST;

import com.example.seatimecalculator2.service.authentificatedUser.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RegistrationREST {
    private final UserService userService;
    @PostMapping("/emailValidation")
    public boolean checkEmailAvailability(@RequestBody String email) {
        return userService.isUserExistsWithSameEmail(email);
    }
}
