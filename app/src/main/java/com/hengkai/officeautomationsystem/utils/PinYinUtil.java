package com.hengkai.officeautomationsystem.utils;

import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by Harry on 2017/10/17.
 * 汉字转换成拼音的工具类
 */

public class PinYinUtil {

    public static String getPinYin(String chinese) {
        if (TextUtils.isEmpty(chinese)) {
            return null;
        }

        //用来设置转化拼音的大小写或者声调之类的
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);  //设置转化的拼音是大写字母
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);   //设置转化的拼音是不需要声调

        //1. 由于只能对单个汉字转化, 所以需要将字符串转化为字符数组, 然后对每个字符转化, 最后拼接起来
        char[] charArray = chinese.toCharArray();
        String pinyin = "";
        for (int i = 0; i < charArray.length; i++) {
            //2. 过滤掉空格
            if (Character.isWhitespace(charArray[i])) {
                continue;
            }
            //3. 需要判断是否是汉字
            //汉字占2个字节, 一个字节的范围是-128~127, 那么汉字肯定大于127
            if (charArray[i] > 127) {
                //可能是汉字
                try {
                    //由于有多音字的存在, 所以这里用到的是数组
                    String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(charArray[i], format);

                    if (pinyinArray != null) {
                        pinyin += pinyinArray[0]; //此处即使有多音字, 那么也只能取第一个拼音
                    } else {
                        //说明没有找到对应的拼音, 汉字有问题或者可能不是汉字, 则忽略
                    }

                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();    //说明转化失败, 不是汉字(例如中文的逗号句号), 那么则忽略
                }
            } else {
                //肯定是不汉字, 应该是键盘上能够直接输入的字符例如 a&*(*等, 这些字符能够排序,但是不能获取拼音, 所以可以直接拼接
                pinyin += charArray[i];
            }
        }
        //过滤掉特殊字符开头的
        if (pinyin.substring(0, 1).matches("[A-Z]")) {
            return pinyin;
        } else {
            return "#" + pinyin;
        }
    }
}
