package com.example.seatimecalculator2.service.seatimeCRUD;

import com.example.seatimecalculator2.entity.SeaTimeEntity;
import com.example.seatimecalculator2.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SeatimeCRUD {
    void addSeaTimeToUser(Long id, SeaTimeEntity seaTimeEntity);

    void updateSeaTime(SeaTimeEntity seaTimeEntity);

    void deleteSeaTime(Long sea_time_entity_id);

    boolean isSeaTimeEnteredValid(SeaTimeEntity seaTimeEntity);

    Page<SeaTimeEntity> getListOfSeaTimeEntities(User user, Pageable pageable);

    SeaTimeEntity getSingleSeaTime(Long sea_time_entity_id);

    String calculateContractLength(SeaTimeEntity seaTimeEntity);

    int calculateContractLengthInDays(SeaTimeEntity seaTimeEntity);

}
