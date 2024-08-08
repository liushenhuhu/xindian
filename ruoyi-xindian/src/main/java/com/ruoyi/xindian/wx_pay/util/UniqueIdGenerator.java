package com.ruoyi.xindian.wx_pay.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class UniqueIdGenerator {

    public static void main(String[] args) {
        String uniqueId = generateUniqueId();
        System.out.println("Generated Unique ID: " + uniqueId);
    }

    public static String generateUniqueId() {
        // 获取当前时间戳
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

        // 生成四位随机数
        Random random = new Random();
        int randomNum = random.nextInt(10000); // 生成0到9999的随机数

        // 格式化随机数为四位数，不足四位时前面补0
        String randomStr = String.format("%04d", randomNum);

        // 组合时间戳和随机数
        return timestamp + randomStr;
    }
}
