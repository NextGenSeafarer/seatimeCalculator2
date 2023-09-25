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
                || seaTimeEntity.getSignOnDate().isEqual(seaTimeEntity.getSignOffDate());
    }

    private String contractLengthCalculation(LocalDate signOn, LocalDate signOff) {
        int daysInFirstMonth = signOn.lengthOfMonth() - signOn.getDayOfMonth();
        int daysInLastMonth = signOff.getDayOfMonth();
        int totalFirstAndLastMonthDays = daysInFirstMonth + daysInLastMonth + 1;
        int totalDaysBetweenDates = (int) ChronoUnit.DAYS.between(signOn, signOff) - totalFirstAndLastMonthDays;
        int monthsBetween = totalDaysBetweenDates / 30;
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
