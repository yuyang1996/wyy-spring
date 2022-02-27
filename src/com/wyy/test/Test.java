package com.wyy.test;

import com.wyy.spring.common.WyyAnnotationApplicationContext;
import com.wyy.spring.common.WyyConfig;
import com.wyy.test.service.HouseService;
import com.wyy.test.service.TestService;
import com.wyy.test.service.proxyinterface.IHouseService;

import java.util.Map;

/**
 * @Author wyy
 * @Date 2022/02/23
 */
public class Test {
    public static void main(String[] args) {
        //自动扫包配置 WyyConfig.class
        //WyyAnnotationApplicationContext wyyAnnotationApplicationContext = new WyyAnnotationApplicationContext(WyyConfig.class);

        // spring容器测试
        // TestService testService = (TestService) wyyAnnotationApplicationContext.getBean("testService");

        // 原型(多例测试)
        // TestService testService = wyyAnnotationApplicationContext.getBean("testService", TestService.class);
        // TestService testService2 = wyyAnnotationApplicationContext.getBean("testService", TestService.class);
        // System.out.println(testService == testService2);

        // 获取单例池，验证懒加载1
        // Map<String, Object> singleMap = wyyAnnotationApplicationContext.getSingleMap();
        // HouseService houseService1 =(HouseService) singleMap.get("houseService");
        // System.out.println(houseService1);
        // 验证懒加载2
        // houseService1 = wyyAnnotationApplicationContext.getBean("houseService", HouseService.class);
        // System.out.println(houseService1);

        // Autowied 自动注入测试
        // TestService testService2 = (TestService) wyyAnnotationApplicationContext.getBean("testService2");
        // testService.test();

        // BeanPostProcessor 前置后置处理器已实现，启动即可
         WyyAnnotationApplicationContext wyyAnnotationApplicationContext = new WyyAnnotationApplicationContext(WyyConfig.class);

        // AOP切面测试待实现
        IHouseService houseService = (IHouseService) wyyAnnotationApplicationContext.getBean("houseService");
        houseService.buy2();
    }
}
