package com.example.seatimecalculator2.controller;

import com.example.seatimecalculator2.entity.user.User;
import com.example.seatimecalculator2.entity.user.accountToken.AccountToken;
import com.example.seatimecalculator2.service.activationToken.AccountTokenService;
import com.example.seatimecalculator2.service.authentificatedUser.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/forgot_password")
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final UserService userService;
    private final AccountTokenService tokenService;
    @Value("${web.site.link.password_reset}")
    String link;

    @GetMapping
    public String forgotPasswordPage() {
        return "forgot_password";
    }

    @GetMapping("/reset/{code}")
    public String changePassword(@PathVariable String code, Model model) {
        AccountToken token = tokenService.findByToken(code).orElseThrow();
        if (token.getExpires_at().isAfter(LocalDateTime.now())) {
            model.addAttribute("user", new User());
            model.addAttribute("token", token.getToken());
            return "change_password";
        }
        return "redirect:/";
    }

    @PostMapping("/reset")
    public String setNewPass(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes model,
                             @ModelAttribute("token") String token) {
        if (bindingResult.hasFieldErrors("password")) {
            return "change_password";
        }
        if (tokenService.changeUserPassword(token, user.getPassword(), user.getPasswordConfirm())) {
            model.addFlashAttribute("message", "Password successfully changed!");
            return "redirect:/login";
        }
        return "change_password";
    }


}
