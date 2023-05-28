package com.example.seatimecalculator2.service.activationToken;

import com.example.seatimecalculator2.entity.user.User;
import com.example.seatimecalculator2.entity.user.activationToken.AccountActivationToken;
import com.example.seatimecalculator2.repository.ActivationTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivationTokenServiceImpl implements ActivationTokenService {
    private final ActivationTokenRepository activationTokenRepository;

    @Override
    public AccountActivationToken createToken(User user) {
        LinkedList<AccountActivationToken> tokenList = activationTokenRepository.findAllByUser(user);
        if (tokenList.size() > 0) {
            log.info("Sending existed token to User with ID: {}", user.getId());
            AccountActivationToken lastToken = tokenList.getLast();
            if (lastToken.getExpires_at().isAfter(LocalDateTime.now())) {
                return lastToken;
            }
        }
        log.info("Creating activation token for User with ID: {}", user.getId());
        AccountActivationToken token = new AccountActivationToken(UUID.randomUUID().toString(), LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15), user);
        token.setCounterSendToEmail(0);
        activationTokenRepository.save(token);
        return token;
    }


    @Override
    public Optional<AccountActivationToken> findByToken(String token) {
        return activationTokenRepository.findByToken(token);
    }

    @Override
    public void saveToken(AccountActivationToken token) {
        activationTokenRepository.save(token);
    }

    @Override
    public void increaseCounterTimesSendToEmail(String token) {
        AccountActivationToken tkn = activationTokenRepository.findByToken(token).orElseThrow();
        int counter = tkn.getCounterSendToEmail();
        counter++;
        tkn.setCounterSendToEmail(counter);
        activationTokenRepository.save(tkn);
    }
}
