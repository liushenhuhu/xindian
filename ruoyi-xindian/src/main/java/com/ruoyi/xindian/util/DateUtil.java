package com.ruoyi.xindian.util;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    /**
     * 通过出生日期获取年龄
     *
     * @param birth
     * @return
     */
    public static int getAge(Date birth) {
        try {
            Calendar cal = Calendar.getInstance();
            int thisYear = cal.get(Calendar.YEAR);
            int thisMonth = cal.get(Calendar.MONTH);
            int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

            cal.setTime(birth);
            int birthYear = cal.get(Calendar.YEAR);
            int birthMonth = cal.get(Calendar.MONTH);
            int birthdayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

            int age = thisYear - birthYear;

            // 未足月
            if (thisMonth <= birthMonth) {
                // 当月
                if (thisMonth == birthMonth) {
                    // 未足日
                    if (dayOfMonth < birthdayOfMonth) {
                        age--;
                    }
                } else {
                    age--;
                }
            }
            return age;
        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * Date转LocalDate
     */
    public static LocalDate getLocalDate(Date date) {
        // 将Date转换为Instant
        Instant instant = date.toInstant();
        // 获取系统默认时区
        ZoneId zoneId = ZoneId.systemDefault();
        // 将Instant转换为ZonedDateTime
        ZonedDateTime zonedDateTime = instant.atZone(zoneId);
        // 从ZonedDateTime获取LocalDate
        return zonedDateTime.toLocalDate();
    }

    public static Date getDateByLocalDate(LocalDate localDate) {
        // 将LocalDate转换为LocalDateTime
        LocalDateTime localDateTime = localDate.atStartOfDay();

        // 获取系统默认时区
        ZoneId zoneId = ZoneId.systemDefault();

        // 将LocalDateTime转换为ZonedDateTime
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);

        // 将ZonedDateTime转换为Instant
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * 将00:00:00类型的字符串转换成汉字字符串
     *
     * @param start
     * @return
     */
    public static String timeToString(String start) {

        String[] split = start.split(":");
        int ss = Integer.parseInt(split[2]);
        int mm = Integer.parseInt(split[1]);
        int hh = Integer.parseInt(split[0]) % 24;
        int dd = Integer.parseInt(split[0]) / 24;
        String res = "";
        if (dd != 0) {
            res += dd + "天";
        }
        if (hh != 0) {
            res += hh + "小时";
        }
        if (mm != 0) {
            res += mm + "分钟";
        }
        if (ss != 0) {
            res += ss + "秒";
        }
        return res;
    }

    /**
     * 时间相减转字符串
     *
     * @param start
     * @param end
     * @return
     */
    public static String twoTimeSubToString(Date start, Date end) {
        long l = end.getTime() - start.getTime();
        int allS = (int) (l / 1000);
        int ss = allS % 60;
        int mm = allS / 60 % 60;
        int hh = allS / 3600 % 24;
        int dd = allS / (3600 * 24);
        String res = "";
        if (dd != 0) {
            res += dd + "天";
        }
        if (hh != 0) {
            res += hh + "小时";
        }
        if (mm != 0) {
            res += mm + "分钟";
        }
        if (ss != 0) {
            res += ss + "秒";
        }
        return res;
    }

    /**
     * 判断字符串是否为合法的日期格式
     *
     * @param dateStr 待判断的字符串
     * @return
     */
    public static boolean isValidDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            format.setLenient(false);
            Date date = format.parse(dateStr);
//            System.out.println(date);
        } catch (Exception e) {
            return false;
        }
        String yearStr = dateStr.split("-")[0];
        return !yearStr.startsWith("0") && yearStr.length() == 4;
    }

    /**
     * 判断时间是星期几
     */
    public static int getWeek(Date date) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = 0;
        try {
            // 将日期字符串转换为Date对象
//            Date date = format.parse(dateString);
            calendar.setTime(date);
            dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dayOfWeek;
    }

    public static String subDay(String lastWeekTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 将字符串解析为LocalDate对象
        LocalDate date = LocalDate.parse(lastWeekTime, formatter);
        // 减去一天
        LocalDate newDate = date.minusDays(1);
        // 将LocalDate对象转换回字符串
        return newDate.format(formatter);
    }

    public static String getLastSunday(String day) {
        DateTimeFormatter formatterString = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 将字符串解析为LocalDate对象
        LocalDate date = LocalDate.parse(day, formatterString);
        LocalDate lastSunday = date.minusWeeks(1).with(DayOfWeek.SUNDAY);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return lastSunday.format(formatter);
    }

    /**
     * 获取今天剩余时间
     *
     * @param
     * @return
     */
    public static Long getToday() {
        // 获取当前时间（包含时区）
        ZonedDateTime now = ZonedDateTime.now();
        // 计算今天的午夜（开始时间）
        ZonedDateTime todayStart = now.truncatedTo(ChronoUnit.DAYS);
        // 计算今天的结束时间（午夜23:59:59.999999999）
        ZonedDateTime todayEnd = todayStart.plusDays(1).minusNanos(1);
        // 计算时间差
        return ChronoUnit.SECONDS.between(now, todayEnd);
    }

    public static String getNowTime() {
        // 获取当前日期
        LocalDateTime currentDate = LocalDateTime.now();
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 将当前日期格式化为字符串
        return currentDate.format(formatter);
    }


    public static Date getNextMonDay(Date startTime) {
        LocalDate localDate = getLocalDate(startTime).plusWeeks(1).with(DayOfWeek.MONDAY);
        return getDateByLocalDate(localDate);
    }
}
