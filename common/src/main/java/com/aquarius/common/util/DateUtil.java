package com.aquarius.common.util;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DateUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.
            ofPattern("yyyy-MM-dd HH:mm:ss");
    public static String format(LocalDateTime time){
        return formatter.format(time);
    }

    public static LocalDateTime parse(String time){
        return LocalDateTime.parse(time,formatter);
    }
}
