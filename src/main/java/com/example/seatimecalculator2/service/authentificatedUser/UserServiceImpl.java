package com.example.seatimecalculator2.service.authentificatedUser;

import com.example.seatimecalculator2.entity.SeaTimeEntity;
import com.example.seatimecalculator2.entity.TotalSeaTimeCounter;
import com.example.seatimecalculator2.entity.user.Role;
import com.example.seatimecalculator2.entity.user.User;
import com.example.seatimecalculator2.entity.user.accountToken.AccountToken;
import com.example.seatimecalculator2.repository.SeaTimeRepository;
import com.example.seatimecalculator2.repository.TotalSeaTimeCounterRepository;
import com.example.seatimecalculator2.repository.UserRepository;
import com.example.seatimecalculator2.service.activationToken.AccountTokenService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final SeaTimeRepository seaTimeRepo;
    private final TotalSeaTimeCounterRepository totalSeaTimeCounterRepository;
    private final AccountTokenService activationTokenService;

    @PostConstruct
    public void createTotalCounterEntry() {
        if (totalSeaTimeCounterRepository.findById(1)
                .isPresent()) {
            return;
        }
        TotalSeaTimeCounter totalSeaTimeCounter = new TotalSeaTimeCounter();
        totalSeaTimeCounter.setLast_updated_at(LocalDateTime.now());
        totalSeaTimeCounter.setCounter(0L);
        totalSeaTimeCounterRepository.save(totalSeaTimeCounter);
    }

    @Override
    public boolean isUserExistsWithSameEmail(String email) {
        log.info("Checking if user with email: {} exists", email);
        return userRepository.findByEmail(email)
                .isPresent();
    }

    @Override
    public String registerUser(User user) {
        if (isUserExistsWithSameEmail(user.getEmail())) {
            return "userExists";
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            return "passwords not matching";
        }
        user.setRegistrationDateAndTime(LocalDateTime.now());
        user.setPassword(encryptPassword(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        log.info("User with id: {} saved to DB successfully", user.getId());
        return "success";

    }

    @Override
    public String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public boolean findAllByUserAndCheckIfContainsEntity(User user, Long sea_time_entity_id) {
        log.info("Checking if user with id: {} has the entity with id: {}", user.getId(), sea_time_entity_id);
        return seaTimeRepo.findAllByUser(user)
                .stream()
                .map(SeaTimeEntity::getId)
                .anyMatch(x -> x.equals(sea_time_entity_id));
    }

    @Override
    public boolean activateAccount(String token) {
        AccountToken tkn = activationTokenService.findByToken(token).orElseThrow();
        if (tkn.getExpires_at().isAfter(LocalDateTime.now())) {
            User user = tkn.getUser();
            user.setActivationCode("activated");
            tkn.setConfirmed_at(LocalDateTime.now());
            activationTokenService.saveToken(tkn);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

}
