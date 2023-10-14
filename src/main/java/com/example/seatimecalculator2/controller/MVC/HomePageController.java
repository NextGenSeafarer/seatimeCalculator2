package com.example.seatimecalculator2.controller.MVC;

import com.example.seatimecalculator2.entity.SeaTimeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomePageController {

    @Value("${link.contact.api}")
    String contactLink;

    @Value("${link.calculation.api}")
    String calculationApi;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("seatime", new SeaTimeEntity());
        model.addAttribute("contactLink", contactLink);
        model.addAttribute("calculationLink", calculationApi);
        return "home";
    }

}
