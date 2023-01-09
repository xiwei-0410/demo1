package com.wxw.repeatedsubmit;

import com.wxw.util.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * 定义重复提交aop
 * @author wxw
 */
@Component
@Aspect
public class RepeatSubmitAop {

    @Autowired
    private RedisCache redisCache;

    @Pointcut("@annotation(com.wxw.repeatedsubmit.RepeatSubmit)")
    public void point(){

    }

    @Around("point()")
    public Object doAround(ProceedingJoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        //这里是唯一标识 根据情况而定
        String key = "1" + "-" + request.getServletPath();
        // 如果缓存中有这个url视为重复提交
        if (!redisCache.haskey(key)) {
            //通过，执行下一步
            Object o = null;
            try {
                o = joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            //然后存入redis 并且设置10s倒计时
            redisCache.setCacheObject(key, 0, 10, TimeUnit.SECONDS);
            //返回结果
            return o;
        } else {
            return Result.error("请勿重复提交或者操作过于频繁！");
        }
    }


}
