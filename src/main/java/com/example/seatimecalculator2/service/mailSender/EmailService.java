package com.example.seatimecalculator2.service.mailSender;

import com.example.seatimecalculator2.entity.EmailDetails;

public interface EmailService {

    String sendSimpleMail(EmailDetails details);
}

