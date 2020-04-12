package com.biao.shop.common.annotation;

import java.lang.annotation.*;

/**
 * @author Biao
 * 使用该注解后，方法将自动生成info级别环绕日志
 */
@Retention(RetentionPolicy.RUNTIME) //保留策略
@Target(ElementType.METHOD) // 目标对象
@Documented // 文档
@Inherited  // 是否继承
public @interface LogAspect {
}
