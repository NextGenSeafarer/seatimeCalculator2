package com.example.seatimecalculator2.service.activationToken;

import com.example.seatimecalculator2.entity.user.User;
import com.example.seatimecalculator2.entity.user.activationToken.AccountActivationToken;

import java.util.Optional;


public interface ActivationTokenService {

    AccountActivationToken createToken(User user);

    Optional<AccountActivationToken> findByToken(String token);

    void saveToken(AccountActivationToken token);

    void increaseCounterTimesSendToEmail(String token);


}
