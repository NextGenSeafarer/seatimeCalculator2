//package com.example.seatimecalculator2;
//
//
//import com.example.seatimecalculator2.service.seaCountingLogic.SeaTimeCountingLogic;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDate;
//import java.util.Random;
//
//import static com.example.seatimecalculator2.enums.ContractLengthCalculation.NOT_CORRECT_DATES;
//import static com.example.seatimecalculator2.enums.ContractLengthCalculation.NOT_CORRECT_VESSEL_NAME;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//class SeatimecalculatorCounterLogicTest {
//
//    SeaTimeCountingLogic seaTimeCountingLogic = new SeaTimeCountingLogic();
//
//    @Test
//    void oneFullYearTest() {
//        for (int i = 0; i < 150; i++) {
//            Random random = new Random();
//            int year = random.nextInt(2000, 2050);
//            int month = random.nextInt(1, 13);
//            int day = random.nextInt(1, LocalDate.of(year, month, 1).lengthOfMonth());
//            LocalDate sign_on = LocalDate.of(year, month, day);
//            LocalDate sign_off = sign_on.plusYears(1);
//            assertEquals(seaTimeCountingLogic.test(sign_on, sign_off), "1:0:1");
//        }
//    }
//
//    @Test
//    void oneDayTest() {
//        for (int i = 0; i < 150; i++) {
//            Random random = new Random();
//            int year = random.nextInt(2000, 2050);
//            int month = random.nextInt(1, 13);
//            int day = random.nextInt(1, LocalDate.of(year, month, 1).lengthOfMonth());
//            LocalDate sign_on = LocalDate.of(year, month, day);
//            LocalDate sign_off = LocalDate.of(year, month, day);
//            assertEquals(seaTimeCountingLogic.test(sign_on, sign_off), "0:0:1");
//        }
//    }
//
//    @Test
//    void oneFullMonthTest() {
//        for (int i = 0; i < 150; i++) {
//            Random random = new Random();
//            int year = random.nextInt(2000, 2050);
//            int month = random.nextInt(1, 12);
//            int day = random.nextInt(1, LocalDate.of(year, month, 1).lengthOfMonth());
//            LocalDate sign_on = LocalDate.of(year, month, day);
//            LocalDate sign_off = sign_on.plusMonths(1);
//            assertEquals(seaTimeCountingLogic.test(sign_on, sign_off), "0:1:0");
//        }
//    }
//
//    @Test
//    void notCorrectDatesTest() {
//        for (int i = 0; i < 150; i++) {
//            Random random = new Random();
//            int year = random.nextInt(2000, 2050);
//            int month = random.nextInt(1, 12);
//            int day = random.nextInt(1, LocalDate.of(year, month, 1).lengthOfMonth());
//            LocalDate sign_off = LocalDate.of(year, month, day);
//            LocalDate sign_on = sign_off.plusDays(1);
//            assertEquals(seaTimeCountingLogic.test(sign_on, sign_off), NOT_CORRECT_DATES);
//        }
//    }
//
//    @Test
//    void notCorrectShipName() {
//        for (int i = 0; i < 50 ; i++) {
//            Random random = new Random();
//            String ship_name = random.nextInt(0, 2) == 0 ? "" : null;
//            int year = random.nextInt(2000, 2050);
//            int month = random.nextInt(1, 12);
//            int day = random.nextInt(1, LocalDate.of(year, month, 1).lengthOfMonth());
//            LocalDate sign_on = LocalDate.of(year, month, day);
//            LocalDate sign_off = sign_on.plusMonths(1);
//            assertEquals(seaTimeCountingLogic.countTheSeaTime(sign_on, sign_off), NOT_CORRECT_VESSEL_NAME);
//        }
//    }
//
//    @Test
//    void randomDatesTest1(){
//        LocalDate sign_on = LocalDate.of(2023, 12, 3);
//        LocalDate sign_off = LocalDate.of(2024,5,1);
//        assertEquals(seaTimeCountingLogic.test(sign_on,sign_off),"0:5:0");
//    }
//
//    @Test
//    void randomDatesTest2(){
//        LocalDate sign_on = LocalDate.of(2023, 1, 1);
//        LocalDate sign_off = LocalDate.of(2023,8,5);
//        assertEquals(seaTimeCountingLogic.test(sign_on,sign_off),"0:7:5");
//    }
//
//    @Test
//    void randomDatesTest3(){
//        LocalDate sign_on = LocalDate.of(2023, 11, 20);
//        LocalDate sign_off = LocalDate.of(2024,1,5);
//        assertEquals(seaTimeCountingLogic.test(sign_on,sign_off),"0:1:16");
//    }
//
//    @Test
//    void randomDatesTest4(){
//        LocalDate sign_on = LocalDate.of(2023, 5, 4);
//        LocalDate sign_off = LocalDate.of(2023,8,30);
//        assertEquals(seaTimeCountingLogic.test(sign_on,sign_off),"0:3:27");
//    }
//    @Test
//    void randomDatesTest5(){
//        LocalDate sign_on = LocalDate.of(2023, 4, 2);
//        LocalDate sign_off = LocalDate.of(2023,7,15);
//        assertEquals(seaTimeCountingLogic.test(sign_on,sign_off),"0:3:14");
//    }
//
//
//
//}
