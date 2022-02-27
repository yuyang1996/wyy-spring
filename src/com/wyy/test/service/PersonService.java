package com.wyy.test.service;

import com.wyy.spring.annotation.Aop;
import com.wyy.spring.annotation.WyyService;
import com.wyy.test.service.proxyinterface.IPersonService;

/**
 * @Author wyy
 * @Date 2022/02/25
 */
@WyyService
@Aop
public class PersonService implements IPersonService {
    public void sayHello(){
        System.out.println("Person：say Hello!");
    }
}
