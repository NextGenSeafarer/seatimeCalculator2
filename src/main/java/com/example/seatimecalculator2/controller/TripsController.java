package com.example.seatimecalculator2.controller;

import com.example.seatimecalculator2.entity.SeaTimeEntity;
import com.example.seatimecalculator2.entity.user.User;
import com.example.seatimecalculator2.service.authentificatedUser.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor

public class TripsController {

    private final UserService userService;


    @PostMapping("/trips")
    public String showAllEntries() {
        return "trips";
    }

    @GetMapping("trips/{sea_time_id}")
    public String showTripById(@PathVariable Long sea_time_id, @AuthenticationPrincipal User user,
                               Model model) {
        model.addAttribute("single_seatime", userService.getSingleSeaTime(user.getId(), sea_time_id));
        return "trips";
    }

    @ModelAttribute(name = "entries")
    private List<SeaTimeEntity> allContracts(@AuthenticationPrincipal User user) {
        return userService.getListOfSeaTimeEntities(user.getId());
    }

}
