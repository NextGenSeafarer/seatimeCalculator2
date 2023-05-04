package com.example.seatimecalculator2.service.authentificatedUser;

import com.example.seatimecalculator2.entity.SeaTimeEntity;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    boolean isUserExistsWithSameEmail(String email);

    boolean registerUser(String first_name,
                      String last_name,
                      String email,
                      String password);

    String encryptPassword(String password);

    void addSeaTimeToUser(Long id, LocalDate sign_on, LocalDate sign_off, String ship_name, String contract_length);

    List<SeaTimeEntity> getListOfSeaTimeEntities(Long id);

    String calculateContractLength(LocalDate beginContractDate, LocalDate endContractDate);

    Long getUserId(String email);

}
