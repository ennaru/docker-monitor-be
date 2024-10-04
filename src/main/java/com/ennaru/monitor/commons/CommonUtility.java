package com.ennaru.monitor.commons;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommonUtility {

    public static String dateFormat() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmmss"));
    }
}
