package org.wayne.common.utils;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 各种路径取值
 * @author: LinWeiQi
 */
public class WhereAmIUtilQ {

    final static Logger log = LoggerFactory.getLogger(WhereAmIUtilQ.class);

    /**
     * 获取IP地址
     */
    public static String getIp4(){
        final String host;
        try {
            InetAddress ip4 = Inet4Address.getLocalHost();
            host = ip4.getHostAddress();
        } catch (UnknownHostException e) {
            log.error("获取Ip地址失败");
            return "";
        }
        return host;
    }

    /**
     * 获取类所在包路径
     */
    public static String getClassPackage(Class<T> clazz){
        return clazz.getPackage().getName();
    }

    public static void whereAmI() throws URISyntaxException {

        System.out.println("java.home : " + System.getProperty("java.home"));

        System.out.println("java.class.version : " + System.getProperty("java.class.version"));

        System.out.println("java.class.path : " + System.getProperty("java.class.path"));

        System.out.println("java.library.path : " + System.getProperty("java.library.path"));

        System.out.println("java.io.tmpdir : " + System.getProperty("java.io.tmpdir"));

        System.out.println("java.compiler : " + System.getProperty("java.compiler"));

        System.out.println("java.ext.dirs : " + System.getProperty("java.ext.dirs"));

        System.out.println("user.name : " + System.getProperty("user.name"));

        System.out.println("user.home : " + System.getProperty("user.home"));

        System.out.println("user.dir : " + System.getProperty("user.dir"));

        System.out.println("当前类所在包路径package: " + WhereAmIUtilQ.class.getPackage().getName());

        String packName = WhereAmIUtilQ.class.getPackage().getName();
        URI packuri = new URI(packName);
        System.out.println("当前类所在URI: " + packuri.getPath());

        /**
         * 测试性能使用
         */
        long startTime = System.nanoTime();//返回最准确的可用系统计时器的当前值，以毫微秒为单位。
        long endTime = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.printf("串行排序花费时间：%d ms", millis);
    }


}
