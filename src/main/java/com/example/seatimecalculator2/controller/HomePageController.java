package com.example.seatimecalculator2.controller;

import com.example.seatimecalculator2.entity.SeaTimeEntity;
import com.example.seatimecalculator2.entity.user.User;
import com.example.seatimecalculator2.service.authentificatedUser.UserService;
import com.example.seatimecalculator2.service.total_time_counter.TotalTimeCounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomePageController {

    private final UserService userService;
    private final TotalTimeCounterService totalTimeCounterService;

    private boolean isUserAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }

    @GetMapping("/")
    public String home(Model model, @RequestParam(required = false) String param) {
        model.addAttribute("seatime", new SeaTimeEntity());
        if (param != null && param.equals("sparrow")) {
            model.addAttribute("total_counter", totalTimeCounterService.getAllSeaTime());
        }
        return "home";
    }

    @PostMapping("/")
    public String seaTimeCalculatorMainPage(@ModelAttribute("seatime") SeaTimeEntity seaTimeEntity,
                                            Model model,
                                            @AuthenticationPrincipal User user) {
        if (userService.isSeaTimeEnteredValid(seaTimeEntity)) {
            String calculatedTime = userService.calculateContractLength(seaTimeEntity);
            seaTimeEntity.setContractLength(calculatedTime);
            seaTimeEntity.setDaysTotal(userService.calculateContractLengthInDays(seaTimeEntity));
            model.addAttribute("currentSeaTime", calculatedTime);
            if (isUserAuthenticated()) {
                userService.addSeaTimeToUser(user.getId(), seaTimeEntity);
            }
        } else {
            model.addAttribute("currentSeaTime", "Check your dates");
        }
        return "home";
    }

    @ModelAttribute(name = "home_page")
    boolean homePage() {
        return true;
    }


}
