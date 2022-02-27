package com.wyy.test.service;

import com.wyy.spring.annotation.WyyAutowired;
import com.wyy.spring.annotation.WyyService;

/**
 * @Author wyy
 * @Date 2022/02/25
 */
@WyyService
public class TestService2 {

    @WyyAutowired
    private TestService3 testService3;

    public void test(){
        testService3.sayHello();
    }
}
