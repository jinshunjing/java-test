package org.jim.java8;

import org.jim.util.DateUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Java 8 引入了新的日期类
 * Clock, ZoneId
 * Instant, Duration
 * LocalDate, LocalDateTime
 * DateTimeFormatter
 *
 * 首先要设置好时钟Clock，包含了时区ZoneId
 * 然后就可以用LocalDate和LocalDateTime来处理日期和时间
 *
 * @author JSJ
 */
public class DateDemo {


    /**
     * 时区
     */
    public static void testZone() {
        // 系统时区
        // ZoneId: {id, offset}
        ZoneId zone = ZoneId.systemDefault();
        System.out.println(zone.getId());

        // 指定时区
        String SHANGHAI_TIMEZONE = "Asia/Shanghai";
        zone = ZoneId.of(SHANGHAI_TIMEZONE);
        System.out.println(zone.getId());
    }

    /**
     * 时钟
     */
    public static void testClock() throws Exception {
        // 系统时钟
        // Clock: ZoneId, mills
        System.out.println(System.currentTimeMillis());
        Thread.sleep(1000L);
        System.out.println(Clock.systemDefaultZone().millis());

        // 指定时区
        Thread.sleep(1000L);
        System.out.println(Clock.systemUTC().millis());
        Thread.sleep(1000L);
        System.out.println(DateUtils.SHANGHAI_CLOCK.millis());

        Instant c = DateUtils.SHANGHAI_CLOCK.instant();
        long st = c.toEpochMilli();
        long ot = st % (1000L * 60L * 60L * 24L);
        long hour = ot / (1000L * 60L * 60L);
        long min = (ot % (1000L * 60L * 60L)) / (1000L * 60L);
        System.out.println(hour + ":" + min);

        LocalTime t = LocalTime.now(Clock.systemUTC());
        System.out.println(t.getHour() + ":" + t.getMinute());

        t = LocalTime.now(DateUtils.SHANGHAI_CLOCK);
        System.out.println(t.getHour() + ":" + t.getMinute());
    }

    /**
     * 时间点和持续时间
     */
    public static void testInstant() {
        // Instant: {seconds, nanos}
        long st = Instant.now().toEpochMilli();
        System.out.println(st);

        // Duration: {seconds, nanos}
        Duration.ofDays(1L);
    }

    /**
     * 日期和时间
     */
    public static void testDateTime() {
        // LocalDate: {year, month, day}
        LocalDate date = LocalDate.now();
        System.out.println(date.getDayOfYear());

        ZoneId zone = ZoneId.systemDefault();
        LocalTime time = LocalTime.now(zone);
        System.out.println(time.toSecondOfDay());

        // 从long到LocalDateTime
        LocalDateTime dateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(System.currentTimeMillis()),
                ZoneId.systemDefault());
        System.out.println(dateTime.format(DateTimeFormatter.ISO_DATE_TIME));

        date = dateTime.toLocalDate();
        time = dateTime.toLocalTime();

    }

    /**
     * 日期格式化
     */
    public static void testDateTimeFormatter() {
        String date = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(Instant.now());
        System.out.println(date);
    }
}
