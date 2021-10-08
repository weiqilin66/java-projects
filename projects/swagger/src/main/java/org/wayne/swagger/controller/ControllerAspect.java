package org.wayne.swagger.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: lwq
 */
@Slf4j
@Component
@Aspect
public class ControllerAspect {

    @Pointcut("execution(* org.wayne.swagger.controller.*.*(..))")
    public void logPointCut(){
    }

    @Before("logPointCut()")
    public void before(JoinPoint joinPoint){
        final Object[] args = joinPoint.getArgs();
        final String method = joinPoint.getSignature().getName();
        log.info("swgger服务:{}方法接收到请求:{}", method, JSONObject.toJSONString(args));
    }
    @AfterReturning(value = "logPointCut()", returning = "result")
    public void afterReturn(JoinPoint joinPoint, Object result) {
        final String method = joinPoint.getSignature().getName();
        log.info("swgger服务:{}方法处理成功响应:{}", method, JSONObject.toJSONString(result));
    }

    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void exceptionBy(JoinPoint joinPoint, Exception e) {
        final String method = joinPoint.getSignature().getName();
        log.info("swgger服务:{}方法处理异常:{}", method, JSONObject.toJSONString(e.toString()));
    }
}
