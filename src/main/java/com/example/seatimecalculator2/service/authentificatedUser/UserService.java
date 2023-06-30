package com.example.seatimecalculator2.service.authentificatedUser;

import com.example.seatimecalculator2.entity.user.User;

public interface UserService {

    boolean isUserExistsWithSameEmail(String email);
    String registerUser(User user);
    String encryptPassword(String password);
    boolean findAllByUserAndCheckIfContainsEntity(User user, Long sea_time_entity_id);
    boolean activateAccount(String token);
    User findUserByEmail(String email);


}
