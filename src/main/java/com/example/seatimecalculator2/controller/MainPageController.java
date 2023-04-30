package com.example.seatimecalculator2.controller;

import com.example.seatimecalculator2.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@AllArgsConstructor
public class MainPageController {

    private UserService userService;



    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("authenticated", userService.isUserAuthorized());


        return "home";
    }

    @PostMapping("/")
    public String addSeaTime(@RequestParam LocalDate begin_contract_date,
                             @RequestParam LocalDate end_contract_date,
                             @RequestParam String vessel_name,
                             Model model) {
        model.addAttribute("currentSeaTime",
                userService.addSeaTimeToUser(begin_contract_date, end_contract_date, vessel_name));
        return "home";
    }

    @GetMapping("/trips/{user_id}")
    public String mainPage(@PathVariable Long user_id, Model model) {
        model.addAttribute("entries", userService.getListOfSeaTimeEntities(user_id));
        return "trips";
    }


}
