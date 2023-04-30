package com.example.seatimecalculator2.service;

import com.example.seatimecalculator2.entity.SeaTimeEntity;
import com.example.seatimecalculator2.entity.User;
import com.example.seatimecalculator2.repository.UserRepo;
import com.example.seatimecalculator2.service.seaCountingLogic.SeaTimeCountingLogic;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.example.seatimecalculator2.enums.ContractLengthCalculation.NOT_CORRECT_DATES;
import static com.example.seatimecalculator2.enums.ContractLengthCalculation.NOT_CORRECT_VESSEL_NAME;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepo userRepository;
    private SeaTimeCountingLogic countingLogic;

    @Override
    public List<User> getAllUsers() {
        log.info("GET ALL USERS");
        return userRepository.findAll();
    }

    @Override
    public String addSeaTimeToUser(LocalDate beginContractDate, LocalDate endContractDate, String vesselName) {
        String calculated = calculateContractLength(beginContractDate, endContractDate, vesselName);
        if (calculated.equals(NOT_CORRECT_DATES) || calculated.equals(NOT_CORRECT_VESSEL_NAME)) {
            return calculated;
        }
        if (isUserAuthorized()) {
            User user = new User();
            SeaTimeEntity seaTimeEntity = new SeaTimeEntity();
            seaTimeEntity.setSignOnDate(beginContractDate);
            seaTimeEntity.setSignOffDate(endContractDate);
            seaTimeEntity.setShipName(vesselName);
            seaTimeEntity.setContractLength(calculated);
            user.addSeaTimeToUser(seaTimeEntity);
            log.info("Adding sea time for {}", user.getId());
            userRepository.save(user);
        }
        return calculated;
    }

    @Override
    public List<SeaTimeEntity> getListOfSeaTimeEntities(Long id) {
        log.info("GET getListOfSeaTimeEntities for user with ID: {}", id);
        if (isUserExists(id)) {
            User user = userRepository.findById(id).get();
            return user.getSeaTimeEntityList();
        }
        return null;
    }

    @Override
    public String calculateContractLength(LocalDate beginContractDate, LocalDate endContractDate, String vesselName) {
        log.info("Calculating sea time, sign_on: {}, sign_off: {}, vessel: {}", beginContractDate, endContractDate, vesselName);
        return countingLogic.countTheSeaTime(beginContractDate, endContractDate, vesselName);
    }

    @Override
    public boolean isUserExists(Long id) {
        log.info("GET info isUserExists with ID: {}", id);
        return userRepository.findById(id).isPresent();
    }

    @Override
    //TODO: just a auth to block adding to DB

    public boolean isUserAuthorized() {
        log.info("Checking if user is authorized");
        return true;
    }

}
