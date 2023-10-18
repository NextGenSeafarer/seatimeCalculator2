package com.example.seatimecalculator2.controller.REST;

import com.example.seatimecalculator2.entity.SeaTimeEntity;
import com.example.seatimecalculator2.entity.user.User;
import com.example.seatimecalculator2.service.authentificatedUser.UserService;
import com.example.seatimecalculator2.service.seatimeCRUD.SeatimeCRUD;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TripsREST {
    private final SeatimeCRUD seatimeCRUD;
    private final UserService userService;

    @PatchMapping("/trips")
    public String updateSeatime(@RequestBody SeaTimeEntity seaTimeEntity, @AuthenticationPrincipal User user) {
        if (userService.findAllByUserAndCheckIfContainsEntity(user, seaTimeEntity.getId())) {
            return seatimeCRUD.updateSeaTime(seaTimeEntity);
        }
        return "Authorization first";
    }

    @DeleteMapping("/trips")
    public boolean deleteEntry(@RequestBody Long id, @AuthenticationPrincipal User user) {
        if (userService.findAllByUserAndCheckIfContainsEntity(user, id)) {
            return seatimeCRUD.deleteSeaTime(id);
        }
        return false;
    }
}
