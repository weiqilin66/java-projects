package org.wayne.common.base;

/**
 * @Description: 实现自定500系统异常返回的实体
 * @author: lwq
 */
public interface IErrorResp<T> {

    abstract T createErrorResp();
}
