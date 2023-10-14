package com.example.seatimecalculator2.controller.MVC;

import com.example.seatimecalculator2.entity.user.User;
import com.example.seatimecalculator2.service.authentificatedUser.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;
    @Value("${link.emailValidation.api}")
    String emailValidationLink;

    @GetMapping("/registration")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("emailValidationLink", emailValidationLink);
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult bindingResult,
                               HttpServletRequest httpServletRequest,
                               RedirectAttributes attributes) throws ServletException {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        attributes.addFlashAttribute("success", true);
        userService.registerUser(user);
        httpServletRequest.logout();
        return "redirect:/login";
    }
}
