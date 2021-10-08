package org.wayne.common.init.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

/**
 * @Description: 缓存读取异常处理器
 * @author: lwq
 */
@Slf4j
public class IgnoreExceptionCacheErrorHandler implements CacheErrorHandler {

    /**
     * 读取缓存异常时忽略缓存返回为空从而去查询数据库 解决了读异常导致的报错影响业务,但是有可能导致缓存雪崩
     * 处理方案1 多级缓存
     * 处理方案2 兜底数据(返回安全的数据)
     * 处理方案3 有高并发的缓存 使用自定方法去写,加互斥锁
     * @param exception
     * @param cache
     * @param key
     */
    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        log.error(exception.getMessage(), exception);
    }

    /**
     * 写缓存异常时,返回空会导致缓存与数据库不一致的问题
     * 思路就是将redis写操作失败的key保存下来，通过重试任务删除这些key对应的redis缓存解决mysql数据与redis缓存数据不一致的问题
     *  handleCachePutError/handleCacheEvictError一样的处理方式
     * @param exception
     * @param cache
     * @param key
     * @param value
     */
    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
        log.error(exception.getMessage(), exception);
    }

    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        log.error(exception.getMessage(), exception);
    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
        log.error(exception.getMessage(), exception);
    }

}
