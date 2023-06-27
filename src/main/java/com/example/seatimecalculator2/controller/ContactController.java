package com.example.seatimecalculator2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController {
    @GetMapping("/contacts")
    public String getContacts(){
        return "contacts";
    }

    //TODO: finish contacts controller that allow to send email from user -> to my mail + response, that email is send successfully
}
