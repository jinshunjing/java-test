package org.jim.util;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;

/**
 * 日期的工具类
 *
 * @author Jim
 */
public class DateUtils {
    public static final String SHANGHAI_TIMEZONE_ID = "Asia/Shanghai";

    public static final ZoneId SHANGHAI_TIMEZONE;
    public static final Clock SHANGHAI_CLOCK;
    static {
        SHANGHAI_TIMEZONE = ZoneId.of(SHANGHAI_TIMEZONE_ID);
        SHANGHAI_CLOCK = Clock.system(SHANGHAI_TIMEZONE);
    }

    public static LocalDateTime toLacalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), SHANGHAI_TIMEZONE);
    }

}
