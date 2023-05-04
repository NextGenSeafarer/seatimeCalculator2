package com.example.seatimecalculator2.controller;

import com.example.seatimecalculator2.entity.SeaTimeEntity;
import com.example.seatimecalculator2.entity.user.User;
import com.example.seatimecalculator2.service.authentificatedUser.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TripsController {

    private final UserService userService;


    @PostMapping("/trips")
    public String mainPage(Model model, @AuthenticationPrincipal User user) {
        List<SeaTimeEntity> listOfUserEntities = userService.getListOfSeaTimeEntities(user.getId());
        if (listOfUserEntities != null) {
            model.addAttribute("entries", listOfUserEntities);
        }
        return "trips";
    }
}
