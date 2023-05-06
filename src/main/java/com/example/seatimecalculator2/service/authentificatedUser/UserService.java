package com.example.seatimecalculator2.service.authentificatedUser;

import com.example.seatimecalculator2.entity.SeaTimeEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserService {

    boolean isUserExistsWithSameEmail(String email);

    boolean registerUser(String first_name,
                         String last_name,
                         String email,
                         String password,
                         String passwordConfirm);

    String encryptPassword(String password);

    void addSeaTimeToUser(Long id, SeaTimeEntity seaTimeEntity);

    List<SeaTimeEntity> getListOfSeaTimeEntities(Long id);

    SeaTimeEntity getSingleSeaTime(Long user_id, Long sea_time_entity_id);

    String calculateContractLength(LocalDate beginContractDate, LocalDate endContractDate);

    Long getUserId(String email);

}
