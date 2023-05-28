package com.example.seatimecalculator2.repository;

import com.example.seatimecalculator2.entity.user.User;
import com.example.seatimecalculator2.entity.user.activationToken.AccountActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.LinkedList;
import java.util.Optional;

public interface ActivationTokenRepository extends JpaRepository<AccountActivationToken, Long> {
    Optional<AccountActivationToken> findByToken(String token);

    LinkedList<AccountActivationToken> findAllByUser(User user);
}
