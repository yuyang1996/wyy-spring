package com.wyy.test.service;

import com.wyy.spring.annotation.Scope;
import com.wyy.spring.annotation.WyyAutowired;
import com.wyy.spring.annotation.WyyService;
import com.wyy.spring.annotation.constant.ScopeEnum;

/**
 * @Author wyy
 * @Date 2022/02/23
 */
@WyyService
@Scope(ScopeEnum.PROTOTYPE)
public class TestService {


    public void test() {
        System.out.println("TestService , succeed!");
    }
}
