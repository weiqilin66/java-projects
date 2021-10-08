package org.wayne.redis.config;

import redis.clients.jedis.Jedis;

/**
 * @Description: 提供接口
 * 1. config中封装连接redis信息
 * 2. 方式使用时， 用匿名类方式重写call方法，使代码简洁
 * @author: LinWeiQi
 */
public interface CallWithJedis {
    /**
     * 获取单例,保证线程安全
     * @param jedis
     */
    void call(Jedis jedis);
}
