package com.hengkai.officeautomationsystem.utils;

import java.text.DecimalFormat;

public class MoneyUtils {

    /**
     * 单位进制
     * @param money 多少元
     * @return
     */
    public static String getUnitMoneyStr(double money){
        DecimalFormat df = new DecimalFormat("#.00");
        String result = "0元";
        if(money > 100000000){
            result = df.format(money/100000000)+"亿";
        } else if(money > 10000){
            result = df.format(money/10000)+"万";
        } else {
            result = df.format(money)+"元";
        }
        return result;
    }

    /**
     * 转换为小数点后两位
     * @param money
     * @return
     */
    public static String getDotTowStr(double money){
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(money) + "元";
    }
}
