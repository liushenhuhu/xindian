package com.ruoyi.xindian;

import java.time.Instant;

public class Main {


    public static void main(String[] args) {

        Instant instant = Instant.now();
        long timestamp = instant.toEpochMilli(); // 转换为毫秒级时间戳
        System.out.println("当前时间戳：" + timestamp);


    }
}
