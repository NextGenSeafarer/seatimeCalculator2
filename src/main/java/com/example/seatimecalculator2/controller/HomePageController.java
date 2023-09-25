package com.example.seatimecalculator2.controller;

import com.example.seatimecalculator2.entity.SeaTimeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomePageController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("seatime", new SeaTimeEntity());
        return "home";
    }

}
