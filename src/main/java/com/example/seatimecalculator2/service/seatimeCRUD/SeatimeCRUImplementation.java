package com.example.seatimecalculator2.service.seatimeCRUD;

import com.example.seatimecalculator2.entity.SeaTimeEntity;
import com.example.seatimecalculator2.entity.user.User;
import com.example.seatimecalculator2.repository.SeaTimeRepository;
import com.example.seatimecalculator2.repository.UserRepository;
import com.example.seatimecalculator2.service.seaCountingLogic.SeaTimeCountingLogic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatimeCRUImplementation implements SeatimeCRUD{
    private final UserRepository userRepository;
    private final SeaTimeCountingLogic countingLogic;
    private final SeaTimeRepository seaTimeRepo;


    @Override
    public void addSeaTimeToUser(Long id,
                                 SeaTimeEntity seaTimeEntity) {
        log.info("Adding sea time {} to user with id: {}", seaTimeEntity, id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        int days = calculateContractLengthInDays(seaTimeEntity);
        user.addSeaTimeEntityToTheList(seaTimeEntity);
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
        }
        seaTimeRepo.save(seaTimeEntity);

    }

    @Override
    public void deleteSeaTime(Long sea_time_entity_id) {
        log.info("Deleting sea time with id: {}", sea_time_entity_id);
        seaTimeRepo.deleteById(sea_time_entity_id);
    }

    @Override
    public boolean isSeaTimeEnteredValid(SeaTimeEntity seaTimeEntity) {
        log.info("Checking if seatime: {} valid", seaTimeEntity);
        return countingLogic.validityOfEnteredDatesCheck(seaTimeEntity);
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
        return (int) ChronoUnit.DAYS.between(seaTimeEntity.getSignOnDate(), seaTimeEntity.getSignOffDate());
    }
}
