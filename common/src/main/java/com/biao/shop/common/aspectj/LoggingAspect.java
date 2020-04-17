package com.biao.shop.common.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @ClassName LogingAspect
 * @Description: TODO
 * @Author Biao
 * @Date 2020/2/5
 * @Version V1.0
 **/
@Aspect
@Component
//@Slf4j
public class LoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    // 前置通知,方法执行之前执行
   @Before("com.biao.shop.common.aspectj.PointcutConf.logPointcut()")
    public void beforeMethod(JoinPoint jp){
       String methodName = jp.getSignature().getName();
       Object[] args = jp.getArgs();
       log.info("BeforeMethod  The method {} parameter is {}",methodName,Arrays.asList(args));
       log.info("add before");
   }

    // 实际使用过程当中，也可以像这样把Advice 和 Pointcut 合在一起，直接在Advice上面定义切入点
    @After("execution(* com.biao.shop.*(..))")
    public void afterMethod() {
       log.info("afterMethod >>>>>>>>>");
    }

    // 使用环绕增强为最常用模式 配合com.biao.shop.common.aspectj.PointcutConf中定义的切点
    @Around("com.biao.shop.common.aspectj.PointcutConf.logPointcut1()")
    public Object aroundMethod(ProceedingJoinPoint jp) throws Throwable {
        String methodName = jp.getSignature().getName();
        Object[] jpArgs = jp.getArgs();
        log.info("before method: {}, args : {}  >>>>>>>>>>>",methodName,jpArgs);
        Object proceed = jp.proceed();
        log.info("After method: {}, args : {}  >>>>>>>>>>>>",methodName,jpArgs);
        return proceed;
    }
}
