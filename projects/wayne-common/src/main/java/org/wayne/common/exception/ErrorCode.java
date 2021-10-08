package org.wayne.common.exception;

import lombok.Data;

/**
 * @Description:
 * @author: lwq
 */
@Data
public class ErrorCode {
    private String errorCode;
    private String errorMsg;

    public ErrorCode(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "错误信息:" + this.errorCode + ":" + this.errorMsg;
    }
}
