package org.wayne.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:  java使用正则表达式
 * @author: lwq
 */
public class RegexDemo {
    public static void main(String[] args) throws Exception {
        t2();
    }

    public static void t1(){
        String content="I am noob from runoob.com";
        String pattern=".*runoob.*";
        boolean isMatch= Pattern.matches(pattern, content);
        System.out.println("字符串中是否包含了‘runoob’子字符串？"+isMatch);
    }

    /**
     * 字符串中是否包含日期
     */
    public static void t2() throws Exception {
        String content = "da.actual_disb_date >= '2021-05-08' and da.actual_disb_date <= '2021-05-08'";
        String pattern = "\\d{4}-\\d{2}-\\d{2}";
        boolean isMatch= Pattern.matches(pattern, content);
        System.out.println("字符串中是否包含了日期子字符串？"+isMatch);
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(content);
        List<String> list = new ArrayList<>();
        while (m.find()) {
            System.out.println("获取到日期字符串 value: " + m.group());
            list.add(m.group());
        }
        if (list.size()!=2) {
            return;
        }
        if (list.get(0).equals(list.get(1))) {
            System.out.println("日期为同一天, 进行日期格式转化");
            final String s = content.replaceFirst(pattern, list.get(0) + " 00:00:00");
            final String s2 = s.replace("'" + list.get(1) + "'", "'"+list.get(1) + " 23:59:59'");;
            System.out.println(s2);
        }
    }
}
