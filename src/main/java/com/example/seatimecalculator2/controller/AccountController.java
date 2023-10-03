package com.example.seatimecalculator2.controller;

import com.example.seatimecalculator2.service.activationToken.AccountTokenService;
import com.example.seatimecalculator2.service.authentificatedUser.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;
    private final AccountTokenService tokenService;
//    @Value("${web.site.link.activation}")
//    String link;


    @GetMapping("/profile")
    public String myPage() {
        return "_blank";
    }

//    @GetMapping("/sendmail")
//    public String sendActivationCode(Model model) {
//        User user = (User) model.getAttribute("user_info");
//        assert user != null;
//        if (user.getActivationCode() != null && user.getActivationCode().equals("activated")) {
//            model.addAttribute("message", "Your account already activated!");
//        } else {
//            model.addAttribute("message", "Activation code was send to you email successfully!");
//            tokenService.sendActivationCode(user, link);
//
//        }
//        return "myAccount";
//    }
//
//    @GetMapping("/activation/{code}")
//    public String activation(@PathVariable String code, Model model, HttpServletRequest httpServletRequest) {
//        if (userService.activateAccount(code)) {
//            model.addAttribute("message", "Account successfully activated!");
//            try {
//                httpServletRequest.logout();
//            } catch (ServletException e) {
//                throw new RuntimeException(e);
//            }
//            return "redirect:/";
//        } else {
//            model.addAttribute("message", "Account is activated already!");
//        }
//        return "myAccount";
//    }
//
//    @ModelAttribute("user_info")
//    User user(@AuthenticationPrincipal User user) {
//        return user;
//    }

}
