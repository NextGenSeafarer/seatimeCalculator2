package com.example.seatimecalculator2.controller;

import com.example.seatimecalculator2.DTO.ContactForm;
import com.example.seatimecalculator2.entity.EmailDetails;
import com.example.seatimecalculator2.service.mailSender.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ContactController {
    private final EmailService service;
    @Value("${spring.mail.username}")
    private String serviceMailReceiver;


    @GetMapping("/contacts")
    public String getContacts(Model model) {
        model.addAttribute("contactDTO", new ContactForm());
        return "contacts";
    }

    @PostMapping("/contacts")
    public String sendContactForm(@ModelAttribute("contactDTO") ContactForm contactForm, Model model) {
        EmailDetails details = new EmailDetails();
        details.setTo(serviceMailReceiver);
        details.setMessage(contactForm.getMessage());
        details.setSubject("Mail from: " + contactForm.getEmail());
        model.addAttribute("message", service.sendSimpleMail(details));
        return "contacts";
    }
}
