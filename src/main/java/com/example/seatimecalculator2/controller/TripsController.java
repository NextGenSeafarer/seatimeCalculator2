package com.example.seatimecalculator2.controller;

import com.example.seatimecalculator2.entity.SeaTimeEntity;
import com.example.seatimecalculator2.entity.user.User;
import com.example.seatimecalculator2.service.authentificatedUser.UserService;
import com.example.seatimecalculator2.service.seatimeCRUD.SeatimeCRUD;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor

public class TripsController {

    private final UserService userService;
    private final SeatimeCRUD crudService;

    @GetMapping("/trips")
    public String showAllTrips(@AuthenticationPrincipal User user,
                               @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                               @RequestParam(name = "size", defaultValue = "6", required = false) int size,
                               Model model) {
        Page<SeaTimeEntity> pageList = crudService.getListOfSeaTimeEntities(user, PageRequest.of(page - 1, size));
        int totalPages = pageList.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("entries", pageList);
        return "trips";
    }

    @GetMapping("/trips/{trip_id}")
    public String showTrip(@PathVariable Long trip_id, Model model, @AuthenticationPrincipal User user) {
        if (userService.findAllByUserAndCheckIfContainsEntity(user, trip_id)) {
            model.addAttribute("single_seatime", crudService.getSingleSeaTime(trip_id));
            return "tripsElements/showTripDetails";
        }
        return "redirect:/trips";


    }

    @GetMapping("/trips/edit/{trip_id}")
    public String editEntityView(@PathVariable Long trip_id, Model model, @AuthenticationPrincipal User user) {
        if (userService.findAllByUserAndCheckIfContainsEntity(user, trip_id)) {
            model.addAttribute("single_seatime", crudService.getSingleSeaTime(trip_id));
            return "tripsElements/edit_trip_details";
        }
        return "redirect:/trips";

    }

    @PatchMapping("/trips/edit/**")
    public String editEntitySave(@ModelAttribute("single_seatime") SeaTimeEntity seaTimeEntity,
                                 RedirectAttributes redirectAttribute, @AuthenticationPrincipal User user) {
        if (!userService.findAllByUserAndCheckIfContainsEntity(user, seaTimeEntity.getId())) {
            return "redirect:/trips";
        }
        if (crudService.isSeaTimeEnteredValid(seaTimeEntity)) {
            crudService.updateSeaTime(seaTimeEntity);
            redirectAttribute.addFlashAttribute("success_editing", true);
        } else {
            redirectAttribute.addFlashAttribute("error_editing", true);
            return "redirect:/trips/edit/" + seaTimeEntity.getId();
        }
        return "redirect:/trips/" + seaTimeEntity.getId();
    }

    @DeleteMapping("/trips/delete/{sea_time_id}")
    public String deleteSeaTimeConfirmationPOST(@PathVariable Long sea_time_id, RedirectAttributes redirectAttribute
            , @AuthenticationPrincipal User user) {
        if (userService.findAllByUserAndCheckIfContainsEntity(user, sea_time_id)) {
            crudService.deleteSeaTime(sea_time_id);
            redirectAttribute.addFlashAttribute("deleted_success", true);
        }
        return "redirect:/trips";
    }


}
