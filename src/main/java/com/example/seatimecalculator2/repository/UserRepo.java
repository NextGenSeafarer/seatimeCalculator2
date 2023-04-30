package com.example.seatimecalculator2.repository;

import com.example.seatimecalculator2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

}
