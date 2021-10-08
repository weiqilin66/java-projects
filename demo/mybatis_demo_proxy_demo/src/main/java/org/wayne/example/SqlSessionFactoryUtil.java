package org.wayne.example;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Description: 单例模式创建SqlSessionFactory==>构造私有化,提供static方法返回唯一单例
 * @author: lwq
 */
public class SqlSessionFactoryUtil {

    private static SqlSessionFactory sqlSessionFactory;
    // 类线程锁
    private static final Class CLASS_LOCK = SqlSessionFactoryUtil.class;

    /**
     * 私有化构造方法,避免通过new创建多个对象,导致多个成员变量SqlSessionFactory
     */
    private SqlSessionFactoryUtil() {
    }

    public static SqlSessionFactory initSqlSessionFactory() {
        String resource = "org/wayne/example/mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * 同步代码块(CLASS_LOCK) 锁的是object类
         * CLASS_LOCK 线程使用了同一个“对象监视器”,所以运行结果是同步的
         */
        synchronized (CLASS_LOCK){
            if (sqlSessionFactory == null) {
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            }
        }
        return sqlSessionFactory;
    }
    public static SqlSession openSqlSession(){
        if (sqlSessionFactory==null) {
            initSqlSessionFactory();
        }
        return sqlSessionFactory.openSession();
    }
}
