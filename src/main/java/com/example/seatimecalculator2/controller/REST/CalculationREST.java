package com.example.seatimecalculator2.controller.REST;

import com.example.seatimecalculator2.entity.SeaTimeEntity;
import com.example.seatimecalculator2.entity.user.User;
import com.example.seatimecalculator2.service.seatimeCRUD.SeatimeCRUD;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CalculationREST {
    private final SeatimeCRUD crudService;

    private boolean isUserAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }


    @PostMapping("/calculate")
    String getCalculatedTime(@RequestBody SeaTimeEntity seaTimeEntity, @AuthenticationPrincipal User user) {
        String calculated;
        if (crudService.isSeaTimeEnteredValid(seaTimeEntity)) {
            calculated = crudService.calculateContractLength(seaTimeEntity);
        } else {
            calculated = "some mistake";
        }
        if (isUserAuthenticated()) {
            seaTimeEntity.setContractLength(calculated);
            crudService.addSeaTimeToUser(user.getId(), seaTimeEntity);
        }

        return calculated;
    }

}
