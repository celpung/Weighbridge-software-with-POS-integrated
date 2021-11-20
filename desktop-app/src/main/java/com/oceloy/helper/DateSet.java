package com.oceloy.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateSet {
    public static String date;

    public static void date_set() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter frdate = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");
        date = frdate.format(now);
    }
}
