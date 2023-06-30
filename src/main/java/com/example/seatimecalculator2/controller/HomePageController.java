package com.example.seatimecalculator2.controller;

import com.example.seatimecalculator2.entity.SeaTimeEntity;
import com.example.seatimecalculator2.entity.user.User;
import com.example.seatimecalculator2.repository.TotalSeaTimeCounterRepository;
import com.example.seatimecalculator2.service.authentificatedUser.UserService;
import com.example.seatimecalculator2.service.seatimeCRUD.SeatimeCRUD;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class HomePageController {

    private final UserService userService;
    private final SeatimeCRUD crudService;
    private final TotalSeaTimeCounterRepository totalSeaTimeCounterRepository;

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
    public String seaTimeCalculatorMainPage(@ModelAttribute("seatime") SeaTimeEntity seaTimeEntity,
                                            Model model,
                                            @AuthenticationPrincipal User user) {
        if (crudService.isSeaTimeEnteredValid(seaTimeEntity)) {
            String calculatedTime = crudService.calculateContractLength(seaTimeEntity);
            model.addAttribute("currentSeaTime", calculatedTime);
            if (isUserAuthenticated()) {
                boolean isShipNameValid = !seaTimeEntity.getShipName().isEmpty() && !seaTimeEntity.getShipName().isBlank() &&
                        seaTimeEntity.getShipName().length() >= 2;
                if (isShipNameValid) {
                    seaTimeEntity.setContractLength(calculatedTime);
                    seaTimeEntity.setDaysTotal(crudService.calculateContractLengthInDays(seaTimeEntity));
                    crudService.addSeaTimeToUser(user.getId(), seaTimeEntity);
                } else {
                    model.addAttribute("currentSeaTime", "Check ship name");
                }
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

    @ModelAttribute(name = "total_counter")
    Long getCounter() {
        return totalSeaTimeCounterRepository.findById(1).orElseThrow().getCounter();
    }


}
