package com.example.seatimecalculator2.controller;

import com.example.seatimecalculator2.entity.user.User;
import com.example.seatimecalculator2.service.authentificatedUser.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class MainPageController {

    private final UserService userService;

    private boolean isUserAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/")
    public String seaTimeCalculatorMainPage(@RequestParam LocalDate sign_on,
                                            @RequestParam LocalDate sign_off,
                                            @RequestParam(required = false, defaultValue = "***") String vessel_name,
                                            Model model,
                                            @AuthenticationPrincipal User user) {
        String calculatedTime = userService.calculateContractLength(sign_on, sign_off);
        model.addAttribute("currentSeaTime", calculatedTime);
        if (isUserAuthenticated()) {
            userService.addSeaTimeToUser(user.getId(), sign_on, sign_off, vessel_name, calculatedTime);
        }
        return "home";
    }




}
