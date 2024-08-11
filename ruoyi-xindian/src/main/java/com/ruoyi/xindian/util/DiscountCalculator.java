package com.ruoyi.xindian.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DiscountCalculator {


    public static void main(String[] args) {
        calculateDiscount(new BigDecimal("699.00"), new BigDecimal("60.00"));
    }

    public static BigDecimal calculateDiscount(BigDecimal originalPrice, BigDecimal discountPercentage) {

        // 计算折扣金额
        BigDecimal discountDecimal = discountPercentage.divide(new BigDecimal("100"));
        BigDecimal originalPriceDecimal = originalPrice.divide(new BigDecimal("100"));
        BigDecimal discountAmount = originalPriceDecimal.multiply(discountDecimal);

        // 计算折扣后的价格
        BigDecimal discountedPrice = originalPrice.subtract(discountAmount);

        // 输出结果
        System.out.println("原价: " + originalPrice);
        System.out.println("折扣金额: " + discountAmount);
        System.out.println("折扣后的价格: " + discountedPrice);


        // 示例数值，有小数点

        // 使用 RoundingMode.CEILING 进位到最近的整数
        BigDecimal roundedValue = discountAmount.setScale(0, RoundingMode.CEILING);

        // 输出结果
        System.out.println("原值: " + discountAmount);
        System.out.println("进位后的整数值: " + roundedValue);
        return roundedValue;
    }
}
