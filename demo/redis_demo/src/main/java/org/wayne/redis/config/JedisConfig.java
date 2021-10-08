package org.wayne.redis.config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Description: 将接口作为参数的封装
 * 执行execute方法传入new 接口对象 就必须实现实现接口
 * execute方法体执行了接口中的方法并传入接口方法参数
 *
 * @author: LinWeiQi
 */
public class JedisConfig {
    private JedisPool jedisPool;

    public JedisConfig(){
        jedisPool=new JedisPool("192.168.45.45");
    }

    public void execute(CallWithJedis callWithJedis) throws Exception {
        //try with source被隐藏了
        try(Jedis jedis = jedisPool.getResource()){
            //封装入参 单例 密码验证
            jedis.auth("123");
            //调用执行execute方式时实现的CallWithJedis接口的call方法
            callWithJedis.call(jedis);
        }

    }
}
