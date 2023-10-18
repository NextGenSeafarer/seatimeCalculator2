package com.example.seatimecalculator2.service.seaCountingLogic;

import com.example.seatimecalculator2.entity.SeaTimeEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeaTimeCountingLogic {

    public String countTheSeaTime(SeaTimeEntity seaTimeEntity) {
        return formattingTheResultString(seaTimeEntity.getSignOnDate(), seaTimeEntity.getSignOffDate());
    }

    public boolean validityOfEnteredDatesCheck(SeaTimeEntity seaTimeEntity) {
        return seaTimeEntity.getSignOnDate().isBefore(seaTimeEntity.getSignOffDate())
                || seaTimeEntity.getSignOnDate().isEqual(seaTimeEntity.getSignOffDate()) || !seaTimeEntity.getShipName().isEmpty();
    }

    private String contractLengthCalculation(LocalDate signOn, LocalDate signOff) {
        if (signOn.isEqual(signOff)) {
            return "0:0:1";
        }
        int daysBetween = (int) ChronoUnit.DAYS.between(signOn, signOff);
        if (signOn.getMonthValue() == signOff.getMonthValue() && signOn.getYear() == signOff.getYear()) {
            int includeLastDay = daysBetween + 1;
            switch (includeLastDay) {
                case 31 -> {
                    return "0:1:0";
                }
                case 30 -> {
                    if (signOn.lengthOfMonth() == 30) {
                        return "0:1:0";
                    } else {
                        return "0:0:30";
                    }
                }
                case 29 -> {
                    if (signOn.getMonthValue() == 2 && signOn.isLeapYear()) {
                        return "0:1:0";
                    }
                }
                case 28 -> {
                    if (signOn.getMonthValue() == 2 && !signOn.isLeapYear()) {
                        return "0:1:0";
                    }
                }
                default -> {
                    return "0:0:" + includeLastDay;
                }
            }
        }
        int daysInFirstMonth = (signOn.lengthOfMonth() - signOn.getDayOfMonth()) +1;

        int daysInLastMonth = signOff.getDayOfMonth();
        int totalFirstAndLastMonthDays = daysInFirstMonth + daysInLastMonth;
        double totalDaysBetweenDates = daysBetween - totalFirstAndLastMonthDays;
        int monthsBetween = (int) Math.round(totalDaysBetweenDates / 30);
        if (totalFirstAndLastMonthDays >= 30) {
            int month = totalFirstAndLastMonthDays / 30;
            monthsBetween += month;
            totalFirstAndLastMonthDays = totalFirstAndLastMonthDays % 30;
        }
        int yearsBetween = 0;
        if (monthsBetween >= 12) {
            yearsBetween = monthsBetween / 12;
            monthsBetween = (monthsBetween - yearsBetween * 12) % 12;
        }
        return yearsBetween + ":" + monthsBetween + ":" + totalFirstAndLastMonthDays;
    }

    private String formattingTheResultString(LocalDate beginContractDate, LocalDate endContractDate) {
        String nonFormatedString = contractLengthCalculation(beginContractDate, endContractDate);

        int year = Integer.parseInt(nonFormatedString.replaceAll(":\\d*", ""));
        int month = Integer.parseInt(nonFormatedString.replaceAll("^\\d*:|:\\d*", ""));
        int day = Integer.parseInt(nonFormatedString.replaceAll("\\d*:", ""));

        String yearOrYears = year == 1 ? " year" : " years";
        String monthOrMonths = month == 1 ? " month" : " months";
        String dayOrDays =
                nonFormatedString.substring(nonFormatedString.indexOf(":") + 1).endsWith("1") ? " day" : " days";
        if (day == 11) {
            dayOrDays = " days";
        }
        String resultYear;
        String resultMonth;
        String resultDay;

        if (month == 0 && day == 0) {
            resultYear = year + yearOrYears;
        } else {
            resultYear = year == 0 ? "" : year + yearOrYears + ", ";
        }
        if (day == 0) {
            resultMonth = month == 0 ? "" : month + monthOrMonths;
            resultDay = "";
        } else {
            resultMonth = month == 0 ? "" : month + monthOrMonths + ", ";
            resultDay = day + dayOrDays;
        }
        return resultYear + resultMonth + resultDay;
    }

}
