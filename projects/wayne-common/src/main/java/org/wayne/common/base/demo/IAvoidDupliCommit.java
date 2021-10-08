package org.wayne.common.base.demo;

/**
 * @Description:
 * @author: lwq
 */
public interface IAvoidDupliCommit {
    String NEED_NOT = "need_not_avoid";
    String CACHE_NAMESPACE = "avoidDuplication";

    /**
     * 幂等的key
     * @return
     */
    String getUniqueIdentify();

    /**
     * 幂等时长
     * @return
     */
    int getExpireTime();
}
