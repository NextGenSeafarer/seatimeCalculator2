package com.example.seatimecalculator2.controller.MVC;

import com.example.seatimecalculator2.entity.user.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class LoginController {

    private boolean isUserAuthenticated() {
        return SecurityContextHolder.getContext()
                                    .getAuthentication() != null &&
                SecurityContextHolder.getContext()
                                     .getAuthentication()
                                     .isAuthenticated() &&
                !(SecurityContextHolder.getContext()
                                       .getAuthentication() instanceof AnonymousAuthenticationToken);
    }

    @GetMapping("/login")
    public String login(@ModelAttribute User user, Model model) {
        model.addAttribute("user", new User());
        if (isUserAuthenticated()) {
            return "redirect:/";
        }
        return "login";
    }
}
