package org.wayne.common.utils;

import org.springframework.util.StringUtils;

import java.util.Scanner;

/**
 * @Description:
 * @author: lwq
 */
public class StringUtilQ {
    public static boolean isEmptyStr(String s) {
        return s == null || s.length() == 0;
    }

    /**
     *     下划线命名转为驼峰命名
     */
    public static String toCamel(String param) {
        StringBuilder result = new StringBuilder();
        String[] arr = param.split("_");
        for (String s : arr) {
            // 传入的值不包含_ 返回原值
            if (!param.contains("_")) {
                result.append(s);
                continue;
            }
            if (result.length() == 0) {
                result.append(s.toLowerCase());
            } else {
                // 字符大写
                result.append(s.substring(0, 1).toUpperCase());
                // 余下小写
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 驼峰命名转为下划线命名
     */
    public static String toSqlStyle(String para) {
        StringBuilder sb = new StringBuilder(para);
        int temp = 0;//定位
        if (!para.contains("_")) {
            for (int i = 0; i < para.length(); i++) {
                if (Character.isUpperCase(para.charAt(i))) {
                    sb.insert(i + temp, "_");
                    temp += 1;
                }
            }
        }
        final String s = sb.toString();
        final String res = s.toLowerCase();
        return res;
    }

    /**
     * @return java.lang.String
     * @Description 入参为提问 并返回下一行控制器输入的值
     */
    public static String sc(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder str = new StringBuilder();
        str.append("请输入" + tip + "：");
        System.out.println(str.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (!StringUtilQ.isEmptyStr(ipt)) {
                return ipt;
            }
        }
        return null;
    }
}
