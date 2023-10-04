package com.example.seatimecalculator2.service.mailSender;

import com.example.seatimecalculator2.entity.EmailDetails;

public interface EmailService {

    boolean sendSimpleMail(EmailDetails details);
}

