package com.ruoyi.xindian.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DiscountCalculator {


    public static void main(String[] args) {
        BigDecimal bigDecimal = calculateDiscount(new BigDecimal("799900.00"), new BigDecimal("60.00"));
        System.out.println(bigDecimal);
    }

    public static BigDecimal calculateDiscount(BigDecimal originalPrice, BigDecimal discountPercentage) {

        // 计算折扣金额
        BigDecimal discountDecimal = discountPercentage.divide(new BigDecimal("100"));
        BigDecimal originalPriceDecimal = originalPrice.divide(new BigDecimal("100"));
        BigDecimal discountAmount = originalPriceDecimal.multiply(discountDecimal);


        // 输出结果
        System.out.println("原价: " + originalPrice);
        System.out.println("折扣金额: " + discountAmount);


        // 示例数值，有小数点

        // 保留两位小数
        BigDecimal discountedPrice = discountAmount.setScale(2, RoundingMode.HALF_UP);

        // 输出结果
        System.out.println("原值: " + discountAmount);
        System.out.println("进位后的整数值: " + discountedPrice);
        return discountedPrice.multiply(new BigDecimal("100"));
    }


}
