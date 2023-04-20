package com.example.seatimecalculator2.service;

import com.example.seatimecalculator2.entity.SeaTimeEntity;
import com.example.seatimecalculator2.entity.User;
import com.example.seatimecalculator2.repository.UserRepo;
import com.example.seatimecalculator2.service.seaCountingLogic.SeaTimeCountingLogic;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UserService {
    UserRepo userRepository;
    SeaTimeCountingLogic countingLogic;

    public boolean isUserExists(Long id) {
        return userRepository.findById(id).isPresent();
    }

    public String executeCounterLogic(LocalDate beginContractDate, LocalDate endContractDate, String vesselName) {
        return countingLogic.countTheSeaTime(beginContractDate, endContractDate, vesselName);
    }


    public void addSeaTimeToUser(LocalDate beginContractDate, LocalDate endContractDate, String vesselName) {
        //TODO: once user login system is out - replace basic user creation from here
        User user = new User();
        SeaTimeEntity seaTimeEntity = new SeaTimeEntity();
        seaTimeEntity.setSignOnDate(beginContractDate);
        seaTimeEntity.setSignOffDate(endContractDate);
        seaTimeEntity.setShipName(vesselName);
        seaTimeEntity.setContractLength(executeCounterLogic(beginContractDate, endContractDate, vesselName));
        user.addSeaTimeToUser(seaTimeEntity);
        userRepository.save(user);
    }

    public List<SeaTimeEntity> listOfSeaTimeEntities(Long id) {
        if (isUserExists(id)) {
            User user = userRepository.findById(id).get();
            return user.getSeaTimeEntityList();
        }
        return null;
    }

    //TODO: just a auth to block adding to DB
    public boolean isUserAuthorized() {
        return false;
    }
}
