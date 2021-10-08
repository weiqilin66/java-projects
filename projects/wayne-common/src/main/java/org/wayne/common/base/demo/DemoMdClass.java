package org.wayne.common.base.demo;

import org.wayne.common.base.Iidempotent;

import java.util.Map;

/**
 * @Description:
 * @author: lwq
 */
public class DemoMdClass implements Iidempotent {


    /**
     * 幂等的keys
     *
     * @return
     */
    @Override
    public String[] redisIdempotentKey() {
        return new String[0];
    }

    /**
     * 幂等时长
     *
     * @return
     */
    @Override
    public int expireTime() {
        return 0;
    }

    /**
     * 数据库幂等的keys
     *
     * @return
     */
    @Override
    public Map<String, Map<String, Object>> dbIdempotentKey() {
        return null;
    }


}
