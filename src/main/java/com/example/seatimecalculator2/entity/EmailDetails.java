package com.example.seatimecalculator2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmailDetails {

    private String to;
    private String subject;
    private String message;
}

