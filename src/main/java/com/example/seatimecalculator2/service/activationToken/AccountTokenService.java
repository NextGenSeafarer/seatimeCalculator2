package com.example.seatimecalculator2.service.activationToken;

import com.example.seatimecalculator2.entity.user.User;
import com.example.seatimecalculator2.entity.user.accountToken.AccountToken;

import java.util.Optional;


public interface AccountTokenService {

    AccountToken createToken(User user);

    Optional<AccountToken> findByToken(String token);

    void saveToken(AccountToken token);

    void increaseCounterTimesSendToEmail(String token);

    boolean changeUserPassword(String token, String new_password, String new_password_confirm);
    boolean sendActivationCode(User user, String link);


}
