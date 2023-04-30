package com.example.seatimecalculator2.service.seaCountingLogic;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static com.example.seatimecalculator2.enums.ContractLengthCalculation.NOT_CORRECT_DATES;
import static com.example.seatimecalculator2.enums.ContractLengthCalculation.NOT_CORRECT_VESSEL_NAME;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeaTimeCountingLogic {

    public String test(LocalDate sign_on, LocalDate sign_off) {
        return contractLengthCalculation(sign_on, sign_off);
    }


    public String countTheSeaTime(LocalDate beginContractDate, LocalDate endContractDate, String vessel_name) {
        if (checkVesselName(vessel_name)) {
            return formattingTheResultString(beginContractDate, endContractDate);
        }
        return NOT_CORRECT_VESSEL_NAME;
    }

    private boolean validityOfEnteredDatesCheck(LocalDate beginContractDate, LocalDate endContractDate) {
        return beginContractDate.isBefore(endContractDate) || beginContractDate.isEqual(endContractDate);
    }

    private String contractLengthCalculation(LocalDate sign_on, LocalDate sign_off) {
        if (validityOfEnteredDatesCheck(sign_on, sign_off)) {
            if (sign_on.isEqual(sign_off)) {
                return "0:0:1";
            }
            if (sign_off.isEqual(sign_on.plusMonths(1))) {
                return "0:1:0";
            }

            int years = sign_off.getYear() - sign_on.getYear();
            int month = sign_off.getMonthValue() - sign_on.getMonthValue();
            if (month < 0) {
                years--;
                month += 12;
            }
            int days = sign_off.getDayOfMonth() - sign_on.getDayOfMonth() + 1;
            if (days < 0) {
                if (month > 0) {
                    month--;
                } else {
                    years--;
                    month = 11;
                }
                days += sign_on.lengthOfMonth();
            }
            if (days == 30) {
                days = 0;
                month++;
            }
            return years + ":" + month + ":" + days;
        }
        return NOT_CORRECT_DATES;
    }

    private String formattingTheResultString(LocalDate beginContractDate, LocalDate endContractDate) {
        String nonFormatedString = contractLengthCalculation(beginContractDate, endContractDate);
        if (nonFormatedString.equals(NOT_CORRECT_DATES)) {
            return NOT_CORRECT_DATES;
        }
        int year = Integer.parseInt(nonFormatedString.replaceAll(":\\d*", ""));
        int month = Integer.parseInt(nonFormatedString.replaceAll("^\\d*:|:\\d*", ""));
        int day = Integer.parseInt(nonFormatedString.replaceAll("\\d*:", ""));

        String yearOrYears = year == 1 ? " year" : " years";
        String monthOrMonths = month == 1 ? " month" : " months";
        String dayOrDays =
                nonFormatedString.substring(nonFormatedString.indexOf(":") + 1).endsWith("1") ? " day" : " days";

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

    private boolean checkVesselName(String vessel_name) {
        return vessel_name != null && !vessel_name.isBlank();
    }


}
