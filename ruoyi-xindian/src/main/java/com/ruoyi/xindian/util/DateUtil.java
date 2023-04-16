package com.ruoyi.xindian.util;

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
    }

    /**
     * 将00:00:00类型的字符串转换成汉字字符串
     * @param start
     * @return
     */
    public static String timeToString(String start){

        String[] split = start.split(":");
        int ss = Integer.parseInt(split[2]);
        int mm = Integer.parseInt(split[1]);
        int hh = Integer.parseInt(split[0])%24;
        int dd = Integer.parseInt(split[0])/24;
        String res="";
        if(dd!=0){
            res+=dd+"天";
        }
        if(hh!=0){
            res+=hh+"小时";
        }
        if(mm!=0){
            res+=mm+"分钟";
        }
        if(ss!=0){
            res+=ss+"秒";
        }
        return res;
    }

    /**
     * 时间相减转字符串
     * @param start
     * @param end
     * @return
     */
    public static String twoTimeSubToString(Date start, Date end){
        long l = end.getTime() - start.getTime();
        int allS= (int) (l/1000);
        int ss = allS%60;
        int mm = allS/60%60;
        int hh = allS/3600%24;
        int dd = allS/(3600*24);
        String res="";
        if(dd!=0){
            res+=dd+"天";
        }
        if(hh!=0){
            res+=hh+"小时";
        }
        if(mm!=0){
            res+=mm+"分钟";
        }
        if(ss!=0){
            res+=ss+"秒";
        }
        return res;
    }
}
