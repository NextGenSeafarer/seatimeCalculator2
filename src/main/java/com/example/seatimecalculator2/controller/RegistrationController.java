package com.example.seatimecalculator2.controller;

import com.example.seatimecalculator2.entity.user.User;
import com.example.seatimecalculator2.service.authentificatedUser.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @GetMapping("/registration")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult bindingResult, Model model, HttpServletRequest httpServletRequest) throws ServletException {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        switch (userService.registerUser(user)) {
            case "success" -> {
                model.addAttribute("success_registered", true);
                httpServletRequest.logout();
                return "login";
            }
            case "userExists" -> {
                model.addAttribute("email_taken", true);
                return "registration";
            }
            case "passwords not matching" -> {
                return "registration";
            }
        }
        return "login";
    }
}
