package com.example.seatimecalculator2.service.seatimeCRUD;

import com.example.seatimecalculator2.entity.SeaTimeEntity;
import com.example.seatimecalculator2.entity.user.User;

import java.util.List;

public interface SeatimeCRUD {
    void addSeaTimeToUser(Long id, SeaTimeEntity seaTimeEntity);

    String updateSeaTime(SeaTimeEntity seaTimeEntity);

    void deleteSeaTime(Long sea_time_entity_id);

    boolean isSeaTimeEnteredValid(SeaTimeEntity seaTimeEntity);

    List<SeaTimeEntity> getListOfSeaTimeEntities(User user);

    SeaTimeEntity getSingleSeaTime(Long sea_time_entity_id);

    String calculateContractLength(SeaTimeEntity seaTimeEntity);

    int calculateContractLengthInDays(SeaTimeEntity seaTimeEntity);

}
