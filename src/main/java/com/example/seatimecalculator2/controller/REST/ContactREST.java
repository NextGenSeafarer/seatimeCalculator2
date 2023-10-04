package com.example.seatimecalculator2.controller.REST;

import com.example.seatimecalculator2.DTO.ContactForm;
import com.example.seatimecalculator2.entity.EmailDetails;
import com.example.seatimecalculator2.service.mailSender.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/api")
public class ContactREST {
    final EmailService service;
    @Value("${spring.mail.username}")
    String serviceMailReceiver;

    @PostMapping("/contact")
    boolean sendContactForm(@RequestBody ContactForm contactForm) {
        EmailDetails details = new EmailDetails();
        details.setTo(serviceMailReceiver);
        details.setMessage("New request from: " + contactForm.getEmail() + ", saying: \n" + contactForm.getMessage());
        details.setSubject("Mail from: " + contactForm.getEmail());
        return service.sendSimpleMail(details);
    }


}
