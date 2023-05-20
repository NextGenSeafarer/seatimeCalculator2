package com.example.seatimecalculator2.service.total_time_counter;

import com.example.seatimecalculator2.entity.SeaTimeEntity;
import com.example.seatimecalculator2.repository.SeaTimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@RequiredArgsConstructor
@Service
@Slf4j

public class TotalTimeCounterServiceImpl implements TotalTimeCounterService {

    private final SeaTimeRepository seaTimeRepository;

    @Override
    public Long getAllSeaTime() {
        log.info("Calculating total sea_time for all users");
        List<SeaTimeEntity> allEntries = seaTimeRepository.findAll();
        AtomicInteger days = new AtomicInteger();
        allEntries.stream().map(SeaTimeEntity::getDaysTotal).forEach(days::addAndGet);
        return days.longValue();
    }
}
