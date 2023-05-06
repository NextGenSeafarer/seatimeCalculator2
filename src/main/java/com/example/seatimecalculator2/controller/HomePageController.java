package com.example.seatimecalculator2.controller;

import com.example.seatimecalculator2.entity.SeaTimeEntity;
import com.example.seatimecalculator2.entity.user.User;
import com.example.seatimecalculator2.service.authentificatedUser.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class HomePageController {

    private final UserService userService;

    private boolean isUserAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("seatime", new SeaTimeEntity());
        return "home";
    }

    @PostMapping("/")
    public String seaTimeCalculatorMainPage(@Valid @ModelAttribute("seatime") SeaTimeEntity seaTimeEntity,
                                            BindingResult bindingResult,
                                            Model model,
                                            @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors()) {
            return "home";
        }
        String calculatedTime = userService.calculateContractLength(seaTimeEntity.getSignOnDate(), seaTimeEntity.getSignOffDate());
        seaTimeEntity.setContractLength(calculatedTime);
        model.addAttribute("currentSeaTime", calculatedTime);
        if (isUserAuthenticated()) {
            userService.addSeaTimeToUser(user.getId(), seaTimeEntity);
        }
        return "home";
    }


}
