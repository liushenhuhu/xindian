package com.ruoyi.xindian.util;
 
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
 
/**
 * 手机号校验工具类
 *
 * @author Duncino
 * @date 2021/12/23
 */
public class PhoneCheckUtils {
 
    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     */
    public static boolean isPhoneLegal(String phone) throws PatternSyntaxException {
        String regex = "^((13[0-9])|(14[05679])|(15([0-3,5-9]))|(16[2567])|(17[0-9])|(18[0-9]|19[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phone);
        return m.matches();
    }
}