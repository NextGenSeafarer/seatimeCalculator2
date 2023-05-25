package com.example.seatimecalculator2.repository;

import com.example.seatimecalculator2.entity.TotalSeaTimeCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TotalSeaTimeCounterRepository extends JpaRepository<TotalSeaTimeCounter, Integer> {
}
