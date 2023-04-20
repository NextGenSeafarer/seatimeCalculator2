package com.example.seatimecalculator2.dbfilling;

import com.example.seatimecalculator2.entity.SeaTimeEntity;
import com.example.seatimecalculator2.entity.User;
import com.example.seatimecalculator2.repository.UserRepo;
import com.example.seatimecalculator2.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
@AllArgsConstructor
public class DBfill {

    private UserService userService;
    private UserRepo userRepository;

    @Transactional
    public void fillTheDb() {
        Random random = new Random();
        List<String> names =
                List.of("Andrey", "Ilia", "Oleg", "Vasilii", "Anton", "Vova", "Alex", "Sergey", "Sereja", "Alexey", "Dima");
        List<String> surnames =
                List.of("Ivanov", "Sergeev", "Bistov", "Letov", "Vasilev", "Vladimirov", "Alerseev", "Dmitriev", "Antonov");


        List<String> ships =
                List.of("Georgiy Ushakov", "Vladimir Voronin", "Nikolay Yevgenov", "Eduard Toll", "Yakov Gakkel", "GS future", "G8 Perseus", "Vision");

        SeaTimeEntity seaTimeEntity = null;
        User user = null;
        for (int i = 0; i < 20; i++) {
            //**************************************************

            List<LocalDateTime> datesAndTime =
                    List.of(LocalDateTime.of(random.nextInt(2000, 2023), random.nextInt(1, 12), random.nextInt(1, 28),
                            random.nextInt(1, 23), random.nextInt(1, 59), random.nextInt(1, 59)));

            //**************************************************

            user = User.builder().name(names.get(random.nextInt(names.size())))
                    .surname(surnames.get(random.nextInt(surnames.size())))
                    .registrationDate(datesAndTime.get(0))
                    .build();


            for (int j = 0; j < random.nextInt(1, 3); j++) {
                //**************************************************
                List<LocalDate> dates =
                        List.of(LocalDate.of(2023, random.nextInt(1, 6), random.nextInt(1, 28)));
                //**************************************************

                LocalDate signOnDate = dates.get(0);
                seaTimeEntity = SeaTimeEntity.builder()
                        .signOnDate(signOnDate)
                        .signOffDate(signOnDate.plusDays(random.nextInt(60, 160)))
                        .shipName(ships.get(random.nextInt(ships.size())))
                        .build();

                userService.addSeaTimeToUser(seaTimeEntity.getSignOnDate(), seaTimeEntity.getSignOffDate(), seaTimeEntity.getShipName());
                userRepository.save(user);
            }
        }
    }

}
