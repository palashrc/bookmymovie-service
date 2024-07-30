package com.bookmymovie.core.util;

import com.bookmymovie.core.constant.CommonConstants;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class CommonUtils {

    public static LocalDateTime getTimeStamp() {
        Instant instant = Instant.ofEpochMilli(System.currentTimeMillis());
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDateTime getConvertedLocalDateTime(String strDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonConstants.MOVIE_SHOW_DATE_TIME_FORMATTER);
        return LocalDateTime.parse(strDate, formatter);
    }
}
