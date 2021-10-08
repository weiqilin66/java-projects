package org.wayne.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 正则工具类
 * @author: lwq
 */
public class ReUtilQ {
    public static final Logger log = LoggerFactory.getLogger(ReUtilQ.class);

    /**
     * 获取匹配的字符,返回第一个匹配
     */
    public static String findAll(String source, String re) {
        //创建Pattern对象
        Pattern p = Pattern.compile(re);
        //创建Matcher对象
        Matcher m = p.matcher(source);
        if (m.find()) {
            int n = m.groupCount();
            log.info("Re:{},匹配次数:{}", re, n);
            log.info("Re Found value: " + m.group(0));
            return m.group(0);
        } else {
            log.info("Re:{} No Match!", re);
            return null;
        }

    }
}

