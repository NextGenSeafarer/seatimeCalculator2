package com.example.seatimecalculator2.controller.REST;

import com.example.seatimecalculator2.entity.SeaTimeEntity;
import com.example.seatimecalculator2.service.seatimeCRUD.SeatimeCRUD;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TripsREST {
    private final SeatimeCRUD seatimeCRUD;

    @PatchMapping("/trips")
    public String updateSeatime(@RequestBody SeaTimeEntity seaTimeEntity) {
        return seatimeCRUD.updateSeaTime(seaTimeEntity);
    }
}
