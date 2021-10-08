package org.wayne.common.init.redis;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.wayne.common.utils.RedisUtil;

import java.lang.reflect.Method;

@EnableCaching //支持缓存注解
@Configuration
@Slf4j
public class RedisConfig extends CachingConfigurerSupport {

    @Autowired
    private RedisConnectionFactory connectionFactory;

    public RedisConfig() {
        log.info("common: 执行redis初始化配置");
    }

    /**
     * 添加自定义缓存异常处理 , 初始建立连接异常不触发
     * 缓存读写异常处理器
     */
    @Override
    public CacheErrorHandler errorHandler() {
        return new IgnoreExceptionCacheErrorHandler();
    }

    /**
     * 声明式缓存(@EnableCaching)管理器
     */
    @Bean
    public CacheManager redisCacheManager() {
        SerializationPair serializationPair = RedisSerializationContext
                .SerializationPair
                .fromSerializer(new GenericFastJsonRedisSerializer());
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
//                .entryTtl(Duration.ofSeconds(30))
                .serializeValuesWith(serializationPair);
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }

    /**
     * 自定义 RedisTemplate Bean
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //对象自动序列化
        //如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to String！
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(connectionFactory);
        final Method setRedisTemplate = ReflectUtil.getMethodByName(RedisUtil.class, "setRedisTemplate");
        ReflectUtil.invoke(RedisUtil.class, setRedisTemplate, redisTemplate);
        return redisTemplate;
    }


}
