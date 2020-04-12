package com.biao.shop.common.annotation;

import java.lang.annotation.*;

/**
 * @author Biao
 * 自定义幂等注解，用于API 层 的 method上
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface AutoIdempotent {
}
