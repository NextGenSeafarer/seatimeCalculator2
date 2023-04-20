package com.example.seatimecalculator2.service.seaCountingLogic;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeaTimeCountingLogic {
    public String countTheSeaTime(LocalDate beginContractDate, LocalDate endContractDate, String vessel_name) {
        if (checkVesselName(vessel_name)) {
            return formattingTheResultString(beginContractDate, endContractDate);
        }
        return "Not valid vessel name";
    }

    private boolean validityOfEnteredDatesCheck(LocalDate beginContractDate, LocalDate endContractDate) {
        if (beginContractDate.isEqual(endContractDate)) {
            return true;
        }
        return beginContractDate.isBefore(endContractDate);
    }

    private String contractLengthCalculation(LocalDate beginContractDate, LocalDate endContractDate) {
        if (validityOfEnteredDatesCheck(beginContractDate, endContractDate)) {
            //<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>> not full days for 1st and last month
            int daysOfTheLastMonth = endContractDate.getDayOfMonth();
            int daysOfTheFirstMonth = 0;
            int fullMonth = 0;
            LocalDate nextMonth = beginContractDate.plusMonths(1);
            LocalDate begDateForCheckAmountOfDaysInMonth = beginContractDate;
            Period period = Period.between(beginContractDate, endContractDate);
            int daysInTheCurrentMonth = 0; // can be 30 || 31 || february (28/29)
            if (beginContractDate.getDayOfMonth() == 1 && period.getMonths() > 1) {
                fullMonth++;
            } else {
                while (begDateForCheckAmountOfDaysInMonth.isBefore(nextMonth) || begDateForCheckAmountOfDaysInMonth.isEqual(nextMonth)) {
                    daysInTheCurrentMonth++;
                    begDateForCheckAmountOfDaysInMonth = begDateForCheckAmountOfDaysInMonth.plusDays(1);
                }
                daysOfTheFirstMonth = daysInTheCurrentMonth - beginContractDate.getDayOfMonth();
            }
            int totalFreeDays = daysOfTheFirstMonth + daysOfTheLastMonth;

            //<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>> full month between start and end of the contract
            LocalDate begMonth = beginContractDate.plusMonths(1);
            while (begMonth.isBefore(endContractDate.minusDays(endContractDate.getDayOfMonth()))) {
                begMonth = begMonth.plusMonths(1);
                fullMonth++;
            }
            int additionalMonth = 0;
            if (period.getMonths() >= 1) {
                additionalMonth = totalFreeDays / 30;
            }
            fullMonth += additionalMonth;
            int additionalDays = totalFreeDays % 30;
            return fullMonth + ":" + additionalDays;
        }
        return "Not_valid";
    }

    private String formattingTheResultString(LocalDate beginContractDate, LocalDate endContractDate) {
        String nonFormatedString = contractLengthCalculation(beginContractDate, endContractDate);
        if (nonFormatedString.equals("Not_valid")) {
            return "Please check your dates";
        }
        int month = Integer.parseInt(nonFormatedString.substring(0, nonFormatedString.indexOf(":")));
        int day = Integer.parseInt(nonFormatedString.substring(nonFormatedString.indexOf(":") + 1));
        int year = 0;
        if (month >= 12) {
            year = month / 12;
            month = (month - 12 * year);
        }

        String yearOrYears = year == 1 ? " year, " : " years, ";
        String monthOrMonths = month == 1 ? " month, " : " months, ";
        String dayOrDays = day == 1 ? " day" : " days";

        String resultYear = year == 0 ? "" : year + yearOrYears;
        String resultMonth = month == 0 ? "" : month + monthOrMonths;
        String resultDay = day == 0 ? "" : day + dayOrDays;

        return resultYear + resultMonth + resultDay;
    }

    private boolean checkVesselName(String vessel_name) {
        return vessel_name != null && !vessel_name.isBlank();
    }


}
