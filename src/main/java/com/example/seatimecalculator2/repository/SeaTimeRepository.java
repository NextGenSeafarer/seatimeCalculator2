package com.example.seatimecalculator2.repository;

import com.example.seatimecalculator2.entity.SeaTimeEntity;
import com.example.seatimecalculator2.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeaTimeRepository extends JpaRepository<SeaTimeEntity, Long> {
    List<SeaTimeEntity> findAllByUser(User user);
}
