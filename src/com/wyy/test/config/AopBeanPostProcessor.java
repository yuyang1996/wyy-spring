package com.wyy.test.config;

import com.wyy.spring.annotation.Aop;
import com.wyy.spring.annotation.WyyService;
import com.wyy.spring.common.BeanPostProcessor;
import com.wyy.test.service.HouseService;
import com.wyy.test.service.proxyinterface.IHouseService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author wyy
 * @Date 2022/02/25
 * 实现AOP的BEAN前置后置处理器
 */
@WyyService
public class AopBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object o) {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object o) {
        // 是否需要代理
        if (o.getClass().isAnnotationPresent(Aop.class)){
            Object proxy = Proxy.newProxyInstance(this.getClass().getClassLoader(), o.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("这里是aop代码");
                    return method.invoke(o, args);
                }
            });
            return proxy;
        }
        return null;
    }
}
