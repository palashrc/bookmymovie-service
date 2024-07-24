package com.bookmymovie.core.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class CommonUtils {

    public static LocalDateTime getTimeStamp() {
        Instant instant = Instant.ofEpochMilli(System.currentTimeMillis());
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
