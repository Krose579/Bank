package com.krose.bank;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class DateUtil {
    private static final String TIMESTAMP_PATTERN = "yyyy-MM-dd";
    private static final SimpleDateFormat formatter = new SimpleDateFormat(TIMESTAMP_PATTERN);

    public static Date today() {
        Calendar date = new GregorianCalendar();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        return date.getTime();
    }

    public static String format(Date date) {
        return formatter.format(date);
    }
}
