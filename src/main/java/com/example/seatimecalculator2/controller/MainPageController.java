package com.example.seatimecalculator2.controller;

import com.example.seatimecalculator2.dbfilling.DBfill;
import com.example.seatimecalculator2.entity.SeaTimeEntity;
import com.example.seatimecalculator2.service.SeaTimeService;
import com.example.seatimecalculator2.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
public class MainPageController {

    private SeaTimeService seaTimeService;
    private UserService userService;

    private DBfill filling;


    @GetMapping("/")
    public String home(Model model) {
//        filling.fillTheDb();
        model.addAttribute("currentSeaTime", "Enter your dates below");
        return "home";
    }

    @PostMapping("/")
    public String addSeaTime(@RequestParam LocalDate begin_contract_date,
                             @RequestParam LocalDate end_contract_date,
                             @RequestParam String vessel_name,
                             Model model) {
        if (userService.isUserAuthorized()) {
            userService.addSeaTimeToUser(begin_contract_date, end_contract_date, vessel_name);
        } else {
            model.addAttribute("currentSeaTime", userService.executeCounterLogic(begin_contract_date,
                    end_contract_date, vessel_name));
        }
        return "home";
    }

    @GetMapping("/trips/{user_id}")
    public String mainPage(Model model, @PathVariable Long user_id) {
        model.addAttribute("entries", userService.listOfSeaTimeEntities(user_id));
        if (model.getAttribute("entries") == null) {
            SeaTimeEntity seaTimeEntity = new SeaTimeEntity();
            seaTimeEntity.setShipName("Sorry you don't have any saved trips yet");
            List<SeaTimeEntity> ls = List.of(seaTimeEntity);
            model.addAttribute("entries", ls);
        }
        return "trips";
    }


}
