package com.example.seatimecalculator2.service.authentificatedUser;

import com.example.seatimecalculator2.entity.SeaTimeEntity;
import com.example.seatimecalculator2.entity.TotalSeaTimeCounter;
import com.example.seatimecalculator2.entity.user.Role;
import com.example.seatimecalculator2.entity.user.User;
import com.example.seatimecalculator2.repository.SeaTimeRepository;
import com.example.seatimecalculator2.repository.TotalSeaTimeCounterRepository;
import com.example.seatimecalculator2.repository.UserRepository;
import com.example.seatimecalculator2.service.seaCountingLogic.SeaTimeCountingLogic;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final SeaTimeCountingLogic countingLogic;
    private final SeaTimeRepository seaTimeRepo;
    private final TotalSeaTimeCounterRepository totalSeaTimeCounterRepository;


    // Initial DB filling
//    @PostConstruct
//    public void fillDb() {
//        Random random = new Random();
//        List<LocalDate> signOnDate = new ArrayList<>();
//        User user = userRepository.findById(4L).get();
//        for (int i = 0; i < 150; i++) {
//            signOnDate.add(
//                    LocalDate.of(
//                            random.nextInt(2000, 2025),
//                            random.nextInt(1, 12),
//                            random.nextInt(1, 25)));
//        }
//        List<LocalDate> signOffDate = new ArrayList<>();
//        for (LocalDate localDate : signOnDate) {
//            signOffDate
//                    .add(localDate
//                            .plusDays(random.nextInt(1, 260)));
//        }
//        List<String> ships = new ArrayList<>();
//        for (int i = 0; i < 400; i++) {
//            ships.add("Ship #" + random.nextInt(0, 1000));
//        }
//
//        List<SeaTimeEntity> list = IntStream.rangeClosed(1, 1000000)
//                .mapToObj(i -> new SeaTimeEntity(
//                        signOnDate.get(random.nextInt(signOnDate.size())),
//                        signOffDate.get(random.nextInt(signOffDate.size())),
//                        ships.get(random.nextInt(ships.size())),
//                        "1 month", 150,
//                        user)).toList();
//        user.setSeaTimeEntityList(list);
//        userRepository.save(user);
//
//    }
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
        log.info("Adding sea time {} to user with id: {}", seaTimeEntity, id);
        User user = userRepository.findById(id)
                                  .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        int days = calculateContractLengthInDays(seaTimeEntity);
        seaTimeEntity.setDaysTotal(days);
        user.addSeaTimeEntityToTheList(seaTimeEntity);
        totalSeaTimeCounterRepository.findById(1)
                                     .orElseThrow()
                                     .updateTotalCounter(days);
        seaTimeRepo.save(seaTimeEntity);
    }

    @Override
    public void updateSeaTime(SeaTimeEntity seaTimeEntity) {
        log.info("Updating seatime: {}", seaTimeEntity);
        SeaTimeEntity existed = seaTimeRepo.findById(seaTimeEntity.getId())
                                           .orElseThrow();
        seaTimeEntity.setUser(existed.getUser());
        if (existed.equals(seaTimeEntity)) {
            log.info("No calculations required for seatime: {}", seaTimeEntity);
            return;
        }
        if (!(existed.getSignOnDate()
                     .equals(seaTimeEntity.getSignOnDate())) ||
                !(existed.getSignOffDate()
                         .equals(seaTimeEntity.getSignOffDate()))) {
            seaTimeEntity.setContractLength(calculateContractLength(seaTimeEntity));
            int days = calculateContractLengthInDays(seaTimeEntity);
            seaTimeEntity.setDaysTotal(days);
            TotalSeaTimeCounter totalSeaTimeCounter = totalSeaTimeCounterRepository.findById(1)
                                                                                   .orElseThrow();
            long daysForUpdate = totalSeaTimeCounter.getCounter();
            daysForUpdate = daysForUpdate - existed.getDaysTotal();
            daysForUpdate = daysForUpdate + days;
            totalSeaTimeCounter.setCounter(daysForUpdate);
            totalSeaTimeCounterRepository.save(totalSeaTimeCounter);
        }
        seaTimeRepo.save(seaTimeEntity);

    }

    @Override
    public void deleteSeaTime(Long sea_time_entity_id) {
        log.info("Deleting sea time with id: {}", sea_time_entity_id);
        int days = seaTimeRepo.findById(sea_time_entity_id)
                              .orElseThrow()
                              .getDaysTotal();
        totalSeaTimeCounterRepository.findById(1)
                                     .orElseThrow()
                                     .updateTotalCounter(-days);
        seaTimeRepo.deleteById(sea_time_entity_id);
    }

    @Override
    public boolean isSeaTimeEnteredValid(SeaTimeEntity seaTimeEntity) {
        log.info("Checking if seatime: {} valid", seaTimeEntity);
        return countingLogic.validityOfEnteredDatesCheck(seaTimeEntity) && seaTimeEntity.getShipName()
                                                                                        .length() > 2;
    }

    @Override
    public Page<SeaTimeEntity> getListOfSeaTimeEntities(User user, Pageable pageable) {
        List<SeaTimeEntity> listOfEntities = userRepository.findById(user.getId())
                                                           .orElseThrow()
                                                           .getSeaTimeEntityList();
        log.info("Getting list of seatime for user with id: {} page: {}, page_size: {}",
                user.getId(),
                pageable.getPageNumber(),
                pageable.getPageSize());
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<SeaTimeEntity> list;
        if (listOfEntities.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, listOfEntities.size());
            list = listOfEntities.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), listOfEntities.size());
    }

    @Override
    public SeaTimeEntity getSingleSeaTime(Long sea_time_entity_id) {
        log.info("Getting single seatime with id: {}", sea_time_entity_id);
        return seaTimeRepo.findById(sea_time_entity_id)
                          .orElseThrow();
    }

    @Override
    public String calculateContractLength(SeaTimeEntity seaTimeEntity) {
        log.info("Calculating sea time, sign_on: {}, sign_off: {}", seaTimeEntity.getSignOnDate(), seaTimeEntity.getSignOffDate());
        return countingLogic.countTheSeaTime(seaTimeEntity);
    }

    @Override
    public int calculateContractLengthInDays(SeaTimeEntity seaTimeEntity) {
        int days = 0;
        LocalDate signOn = seaTimeEntity.getSignOnDate();
        while (signOn.isBefore(seaTimeEntity.getSignOffDate()) ||
                (signOn.isEqual(seaTimeEntity.getSignOffDate()))) {
            signOn = signOn.plusDays(1);
            days++;
        }
        return days;
    }

    @Override
    public boolean findAllByUserAndCheckIfContainsEntity(User user, Long sea_time_entity_id) {
        log.info("Checking if user with id: {} has the entity with id: {}", user.getId(), sea_time_entity_id);
        return seaTimeRepo.findAllByUser(user)
                          .stream()
                          .map(SeaTimeEntity::getId)
                          .anyMatch(x -> x.equals(sea_time_entity_id));
    }
}
