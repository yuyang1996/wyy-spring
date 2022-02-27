package com.wyy.test.config;

import com.wyy.spring.annotation.WyyService;
import com.wyy.spring.common.BeanPostProcessor;

/**
 * @Author wyy
 * @Date 2022/02/25
 *
 * 扩展的BEAN前置后置处理器
 */
@WyyService
public class TestBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object o) {
        System.out.println("经过前置处理器：" + o);
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o) {
        return o;
    }


}
