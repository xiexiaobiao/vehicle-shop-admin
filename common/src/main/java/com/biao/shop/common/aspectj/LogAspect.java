package com.biao.shop.common.aspectj;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME) //保留策略
@Target(ElementType.METHOD) // 目标对象
@Documented // 文档
@Inherited  // 是否继承
public @interface LogAspect {
}
