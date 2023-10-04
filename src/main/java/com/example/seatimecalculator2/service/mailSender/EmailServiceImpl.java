package com.example.seatimecalculator2.service.mailSender;

import com.example.seatimecalculator2.entity.EmailDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public boolean sendSimpleMail(EmailDetails details) {
        SimpleMailMessage mailMessage
                = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setTo(details.getTo());
        mailMessage.setText(details.getMessage());
        mailMessage.setSubject(details.getSubject());
        try {
            javaMailSender.send(mailMessage);
        } catch (MailException e) {
            return false;
        }
        return true;
    }


}

