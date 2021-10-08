package org.wayne.common.exception;

import lombok.Data;

/**
 * @Description: 上锁失败
 * @author: lwq
 */
@Data
public class LockException extends Exception{
    private String errCode;
    private static final long serialVersionUID = 1L;

    /**
     * 自带信息和抛出异常
     */
    public LockException(String message, Throwable t) {
        super(message, t);
    }

    public LockException(ErrorCode errorCode) {
        super(errorCode.getErrorCode() + ":" + errorCode.getErrorMsg());
        this.errCode = errorCode.getErrorCode();
    }

    public LockException(ErrorCode errorCode, Throwable t) {
        super(errorCode.getErrorCode() + ":" + errorCode.getErrorMsg(), t);
        this.errCode = errorCode.getErrorCode();
    }

    public LockException(String message) {
        super(message);
    }

    public LockException(String errCode, String message) {
        super(errCode + ":" + message);
        this.errCode = errCode;
    }

}
