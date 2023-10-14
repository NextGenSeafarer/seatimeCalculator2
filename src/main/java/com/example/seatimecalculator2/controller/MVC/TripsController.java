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
        List<SeaTimeEntity> list = crudService.getListOfSeaTimeEntities(user);
        model.addAttribute("voyages", list);
        model.addAttribute("tripsLink", tripsLink);
        return "trips";
    }
//
//    @GetMapping("/trips/{trip_id}")
//    public String showTrip(@PathVariable Long trip_id, Model model, @AuthenticationPrincipal User user) {
//        if (userService.findAllByUserAndCheckIfContainsEntity(user, trip_id)) {
//            model.addAttribute("single_seatime", crudService.getSingleSeaTime(trip_id));
//            return "tripsElements/showTripDetails";
//        }
//        return "redirect:/trips";
//
//
//    }
//
//    @GetMapping("/trips/edit/{trip_id}")
//    public String editEntityView(@PathVariable Long trip_id, Model model, @AuthenticationPrincipal User user) {
//        if (userService.findAllByUserAndCheckIfContainsEntity(user, trip_id)) {
//            model.addAttribute("single_seatime", crudService.getSingleSeaTime(trip_id));
//            return "tripsElements/edit_trip_details";
//        }
//        return "redirect:/trips";
//
//    }
//
//    @PatchMapping("/trips/edit/**")
//    public String editEntitySave(@ModelAttribute("single_seatime") SeaTimeEntity seaTimeEntity,
//                                 RedirectAttributes redirectAttribute, @AuthenticationPrincipal User user) {
//        if (!userService.findAllByUserAndCheckIfContainsEntity(user, seaTimeEntity.getId())) {
//            return "redirect:/trips";
//        }
//        if (crudService.isSeaTimeEnteredValid(seaTimeEntity)) {
//            crudService.updateSeaTime(seaTimeEntity);
//            redirectAttribute.addFlashAttribute("success_editing", true);
//        } else {
//            redirectAttribute.addFlashAttribute("error_editing", true);
//            return "redirect:/trips/edit/" + seaTimeEntity.getId();
//        }
//        return "redirect:/trips/" + seaTimeEntity.getId();
//    }
//
//    @DeleteMapping("/trips/delete/{sea_time_id}")
//    public String deleteSeaTimeConfirmationPOST(@PathVariable Long sea_time_id, RedirectAttributes redirectAttribute
//            , @AuthenticationPrincipal User user) {
//        if (userService.findAllByUserAndCheckIfContainsEntity(user, sea_time_id)) {
//            crudService.deleteSeaTime(sea_time_id);
//            redirectAttribute.addFlashAttribute("deleted_success", true);
//        }
//        return "redirect:/trips";
//    }


}
