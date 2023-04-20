package com.example.seatimecalculator2.service;

import com.example.seatimecalculator2.repository.SeaTimeRepo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SeaTimeService {
    SeaTimeRepo seaTimeRepository;

    public void deleteById(Long id) {
        seaTimeRepository.deleteById(id);
    }



}
