package com.example.seatimecalculator2.controller.MVC;

import com.example.seatimecalculator2.entity.user.User;
import com.example.seatimecalculator2.entity.user.accountToken.AccountToken;
import com.example.seatimecalculator2.service.activationToken.AccountTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/forgot_password")
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final AccountTokenService tokenService;
    @Value("${link.resetPassword.api}")
    private String apiLink;

    @GetMapping
    public String forgotPasswordPage(Model model) {
        model.addAttribute("apiLink", apiLink);
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
    public String setNewPass(@ModelAttribute("user") User user, RedirectAttributes model,
                             @ModelAttribute("token") String token) {
        if (tokenService.changeUserPassword(token, user.getPassword(), user.getPasswordConfirm())) {
            model.addFlashAttribute("success_password_change", true);
            return "redirect:/login";
        }
        return "change_password";
    }

}
