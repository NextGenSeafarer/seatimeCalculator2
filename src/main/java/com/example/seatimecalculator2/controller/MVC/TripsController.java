package com.example.seatimecalculator2.controller.MVC;

import com.example.seatimecalculator2.entity.SeaTimeEntity;
import com.example.seatimecalculator2.entity.user.User;
import com.example.seatimecalculator2.service.seatimeCRUD.SeatimeCRUD;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor

public class TripsController {

    private final SeatimeCRUD crudService;
    @Value("${link.trips.api}")
    String tripsLink;

    @GetMapping("/trips")
    public String showAllTrips(@AuthenticationPrincipal User user,
                               Model model) {
        model.addAttribute("tripsLink", tripsLink);
        List<SeaTimeEntity> list = crudService.getListOfSeaTimeEntities(user);
        if (list.isEmpty()) {
            model.addAttribute("empty_list", true);
        } else {
            model.addAttribute("voyages", list);
        }


        return "trips";
    }

}
