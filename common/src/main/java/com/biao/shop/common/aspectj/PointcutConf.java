package com.biao.shop.common.aspectj;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareAnnotation;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @ClassName PointcutConf
 * @Description: TODO
 * @Author Biao
 * @Date 2020/2/5
 * @Version V1.0
 **/
@Aspect
@Component
public class PointcutConf {
    /**
     *定义一个方法,用于声明切点表达式,该方法一般没有方法体
     *@Pointcut用来声明切点表达式
     * 一般将所有切入点定义到一个类中，集中管理，
     *通知直接使用定义的方法名即可引入当前的切点表达式
     */
    /**
     * 使用@Aspect实现AOP，实际上是动态代理*/
    @Pointcut("execution(* com.biao.shop.*(..))")
    public void logPointcut(){
    }

    // 标记了@LogAspect注解的方法就进行AOP
    @Pointcut(value = "@annotation(com.biao.shop.common.aspectj.LogAspect)")
    public void logPointcut1(){
    }
}
