package com.example.seatimecalculator2.controller;

import com.example.seatimecalculator2.entity.user.User;
import com.example.seatimecalculator2.service.authentificatedUser.UserService;
import com.example.seatimecalculator2.service.authentificatedUser.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;
    @Value("${web.site.link.activation}")
    String link;


    @GetMapping()
    public String myPage() {
        return "myAccount";
    }

    @GetMapping("/sendmail")
    public String sendActivationCode(Model model) {
        User user = (User) model.getAttribute("user_info");
        assert user != null;
        if (user.getActivationCode() != null && user.getActivationCode().equals("activated")) {
            model.addAttribute("message", "Your account already activated!");
        } else {
            model.addAttribute("message", "Activation code was send to you email successfully!");
            userService.sendActivationCode(user, link);

        }
        return "myAccount";
    }

    @GetMapping("/activation/{code}")
    public String activation(@PathVariable String code, Model model) {
        if (userService.activateAccount(code)) {
            model.addAttribute("message", "Account successfully activated!");
        }else {
            model.addAttribute("message", "Account is activated already!");
        }
        return "myAccount";
    }

    @ModelAttribute("user_info")
    User user(@AuthenticationPrincipal User user) {
        return user;
    }

}
