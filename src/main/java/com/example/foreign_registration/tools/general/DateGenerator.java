package com.example.foreign_registration.tools.general;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateGenerator {

    public static Date getCurrentDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime localDateTime = LocalDateTime.now();
        return Date.valueOf(dateTimeFormatter.format(localDateTime));
    }

    public static String getCurrentYearText() {
        return getCurrentDate().toString().substring(0, 4);
    }

    public static Date getFistDateOfCurrentYear() {
        String firstOfJanuary = "01-01";
        return Date.valueOf(getCurrentYearText() + '-' + firstOfJanuary);
    }

}
