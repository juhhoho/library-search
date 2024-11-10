package com.library.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static final DateTimeFormatter YYYYMMDD_FORMATER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static LocalDate parseYYYYMMDD(String date){
        return LocalDate.parse(date, YYYYMMDD_FORMATER);
    }

    public static LocalDateTime parseOffsetDateTime(String datetime) {
        return LocalDateTime.parse(datetime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
