package com.wyy.spring.annotation;

import com.wyy.spring.annotation.constant.ScopeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author wyy
 * @Date 2022/02/24
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {
    ScopeEnum value() default ScopeEnum.SINGLE;
}
