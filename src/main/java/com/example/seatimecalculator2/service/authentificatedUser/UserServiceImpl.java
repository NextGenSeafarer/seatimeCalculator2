package com.example.seatimecalculator2.service.authentificatedUser;

import com.example.seatimecalculator2.entity.SeaTimeEntity;
import com.example.seatimecalculator2.entity.user.Role;
import com.example.seatimecalculator2.entity.user.User;
import com.example.seatimecalculator2.repository.UserRepository;
import com.example.seatimecalculator2.service.seaCountingLogic.SeaTimeCountingLogic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final SeaTimeCountingLogic countingLogic;


    @Override
    public boolean isUserExistsWithSameEmail(String email) {
        log.info("Checking if user with email: {} exists", email);
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public boolean registerUser(String first_name, String last_name, String email, String password, String passwordConfirm) {
        if (!password.equals(passwordConfirm)) {
            return false;
        }
        User user = new User();
        user.setFirstname(first_name);
        user.setLastname(last_name);
        user.setEmail(email);
        user.setRegistrationDateAndTime(LocalDateTime.now());
        user.setPassword(encryptPassword(password));
        user.setRole(Role.USER);
        userRepository.save(user);
        log.info("User with id: {} saved to DB successfully", user.getId());
        return true;

    }

    @Override
    public String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public void addSeaTimeToUser(Long id,
                                 SeaTimeEntity seaTimeEntity) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.addSeaTimeEntityToTheList(seaTimeEntity);
        userRepository.save(user);


    }

    @Override
    public List<SeaTimeEntity> getListOfSeaTimeEntities(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User found found"))
                .getSeaTimeEntityList();
    }

    @Override
    public SeaTimeEntity getSingleSeaTime(Long user_id, Long sea_time_entity_id) {
        return getListOfSeaTimeEntities(user_id).stream().filter(x -> x.getId() == sea_time_entity_id).toList().get(0);
    }

    @Override
    public String calculateContractLength(LocalDate beginContractDate, LocalDate endContractDate) {
        log.info("Calculating sea time, sign_on: {}, sign_off: {}", beginContractDate, endContractDate);
        return countingLogic.countTheSeaTime(beginContractDate, endContractDate);
    }

    @Override
    public Long getUserId(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User is not found")).getId();
    }
}
