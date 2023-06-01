package com.example.seatimecalculator2.service.activationToken;

import com.example.seatimecalculator2.entity.user.User;
import com.example.seatimecalculator2.entity.user.accountToken.AccountToken;
import com.example.seatimecalculator2.repository.AccountTokenRepository;
import com.example.seatimecalculator2.repository.UserRepository;
import com.example.seatimecalculator2.service.authentificatedUser.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountTokenServiceImpl implements AccountTokenService {
    private final UserRepository userRepository;
    private final AccountTokenRepository accountTokenRepository;

    @Override
    public AccountToken createToken(User user) {
        LinkedList<AccountToken> tokenList = accountTokenRepository.findAllByUser(user);
        if (tokenList.size() > 0) {
            log.info("Sending existed token to User with ID: {}", user.getId());
            AccountToken lastToken = tokenList.getLast();
            if (lastToken.getExpires_at().isAfter(LocalDateTime.now()) && (lastToken.getConfirmed_at() == null)) {
                return lastToken;
            }
        }
        log.info("Creating activation token for User with ID: {}", user.getId());
        AccountToken token = new AccountToken(UUID.randomUUID().toString(), LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15), user);
        token.setCounterSendToEmail(0);
        accountTokenRepository.save(token);
        return token;
    }


    @Override
    public Optional<AccountToken> findByToken(String token) {
        return accountTokenRepository.findByToken(token);
    }

    @Override
    public void saveToken(AccountToken token) {
        accountTokenRepository.save(token);
    }

    @Override
    public void increaseCounterTimesSendToEmail(String token) {
        AccountToken tkn = accountTokenRepository.findByToken(token).orElseThrow();
        int counter = tkn.getCounterSendToEmail();
        counter++;
        tkn.setCounterSendToEmail(counter);
        accountTokenRepository.save(tkn);
    }

    @Override
    public boolean changeUserPassword(String token, String new_password, String new_password_confirm) {
        if (!new_password.equals(new_password_confirm)) {
            return false;
        }
        AccountToken tkn = accountTokenRepository.findByToken(token).orElseThrow();
        if (tkn.getConfirmed_at() == null) {
            User user = tkn.getUser();
            user.setPassword(new BCryptPasswordEncoder().encode(new_password));
            tkn.setConfirmed_at(LocalDateTime.now());
            accountTokenRepository.save(tkn);
            userRepository.save(user);
            return true;
        }
        return false;

    }
}
