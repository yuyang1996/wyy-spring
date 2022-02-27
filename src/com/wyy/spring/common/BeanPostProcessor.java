package com.wyy.spring.common;

/**
 * @Author wyy
 * @Date 2022/02/24
 *
 * bean全局前置后置处理器
 */
public interface BeanPostProcessor {

    Object postProcessBeforeInitialization(Object o);

    Object postProcessAfterInitialization(Object o);

}
