package com.biao.shop.common.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
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
public class LoggingAspect {

    // 前置通知,方法执行之前执行
   @Before("com.biao.shop.common.aspectj.PointcutConf.logPointcut()")
    public void beforeMethod(JoinPoint jp){
       String methodName = jp.getSignature().getName();
       Object[] args = jp.getArgs();
       System.out.println("BeforeMethod  The method   "+ methodName +"   parameter is  "+ Arrays.asList(args));
       System.out.println("add before");
   }

    // 实际使用过程当中，也可以像这样把Advice 和 Pointcut 合在一起，直接在Advice上面定义切入点
    @After("execution(* com.biao.shop.*(..))")
    public void afterMethod() {
        System.out.println("afterMethod >>>>>>>>>");
    }

    // 使用环绕增强为最常用模式
    @Around("com.biao.shop.common.aspectj.PointcutConf.logPointcut1()")
    public void aroundMethod(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("before >>>>>>>>>>>");
        jp.proceed();
        System.out.println("After >>>>>>>>>>>>");

    }
}
