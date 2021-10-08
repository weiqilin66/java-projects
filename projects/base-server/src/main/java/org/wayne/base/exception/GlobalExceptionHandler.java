package org.wayne.base.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.wayne.api.entity.RespBeanQ;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @Description: 全局异常处理
 * 定义规则: 定义捕捉异常的父类可捕捉该类异常统一处理,例如sql的外键异常,主键为空异常其实都是继承了某个父类 直接捕捉该父类
 * @author: LinWeiQi
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLException.class)
    public RespBeanQ SQLException(SQLException e) {
        if (e instanceof SQLIntegrityConstraintViolationException) {
            return RespBeanQ.error("该数据有外键关联,无法删除,操作失败!");
        }
        // 可以继续判断添加详细的异常捕捉

        return RespBeanQ.error("数据库异常,操作失败!");
    }
}
