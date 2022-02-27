package com.wyy.test.service;

import com.wyy.spring.annotation.Aop;
import com.wyy.spring.annotation.Lazy;
import com.wyy.spring.annotation.WyyService;
import com.wyy.test.Test;
import com.wyy.test.service.proxyinterface.IHouseService;

/**
 * @Author wyy
 * @Date 2022/02/23
 */
@WyyService
@Lazy
@Aop
public class HouseService implements IHouseService {

    public void buyHouse(){
        System.out.println("I Want Buy Two House!");
    }

    @Override
    public void buy2() {
        System.out.println("I Want Buy big House!");
    }
}
