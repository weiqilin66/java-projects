package org.wayne.common.base;

import java.util.Map;

/**
 * @Description: 幂等校验实现此接口
 * @author: lwq
 */
public interface Iidempotent {

    /**
     * 幂等的keys
     * @return
     */
    String[] redisIdempotentKey();

    /**
     * 幂等时长
     * @return
     */
    int expireTime();

    /**
     * 数据库幂等的keys
     * @return
     */
    Map<String,Map<String,Object>> dbIdempotentKey();
}
