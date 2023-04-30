package com.example.seatimecalculator2.service;

import com.example.seatimecalculator2.entity.SeaTimeEntity;
import com.example.seatimecalculator2.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    String addSeaTimeToUser(LocalDate beginContractDate, LocalDate endContractDate, String vesselName);

    List<SeaTimeEntity> getListOfSeaTimeEntities(Long id);

    String calculateContractLength(LocalDate beginContractDate, LocalDate endContractDate, String vesselName);

    boolean isUserExists(Long id);

    boolean isUserAuthorized();


}
