package com.wyy.spring.common;


import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.*;

import com.wyy.spring.annotation.*;
import com.wyy.spring.annotation.constant.ScopeEnum;
import com.wyy.test.config.AopBeanPostProcessor;

/**
 * @Author wyy
 * @Date 2022/02/23
 */
public class WyyAnnotationApplicationContext {

    // 单例池
    private static Map<String, Object> singleBeanMap = new HashMap<>();
    private static Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    private static List<String> beanPostProcessorNameList = new ArrayList<>();

    private Class config;

    public WyyAnnotationApplicationContext(Class clazz) {
        config = clazz;
        scan();
    }

    private void scan() {
        if (config.isAnnotationPresent(Scan.class)) {
            Scan scan = (Scan) config.getAnnotation(Scan.class);
            String javaPath = scan.value();
            String windowsPath = javaPath.replace('.', '/');
            // 获取当前类加载器
            ClassLoader classLoader = WyyAnnotationApplicationContext.class.getClassLoader();
            URL url = classLoader.getResource(windowsPath);
            File directory = new File(url.getPath());
            List<String> filePathList = WyyUtil.getAllFilePath(directory, javaPath.substring(0, javaPath.lastIndexOf('.')) + '.' + directory.getName());

            // 预处理 缓存Bean定义，事先创建bean前置后置处理器
            for (String filePath : filePathList) {
                try {
                    Class<?> clazz = classLoader.loadClass(filePath);
                    if (clazz.isAnnotationPresent(WyyService.class)) {

                        String beanName = WyyUtil.getBeanNameByClass(clazz.getSimpleName());

                        // 加载BeanDefinition并缓存，方便后续构建bean时不需要重新反射获取class信息 (spring中是通过ASM中获取class信息)
                        loadBeanDefinition(clazz, beanName);

                        // 先创建前置后置处理器实现器
                        if (BeanPostProcessor.class.isAssignableFrom(clazz)) {
                            beanPostProcessorNameList.add(beanName);
                            crateBean(beanName, true);
                        }

                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
                // 创建实例，懒加载的不立即创建
                crateBean(entry.getKey(), false);
            }
        }


    }

    private void loadBeanDefinition(Class<?> clazz, String beanName) {
        if (Objects.isNull(clazz) || Objects.isNull(beanName) || beanName == "")
            throw new RuntimeException("构建BeanDefinition失败，bean信息为空");

        BeanDefinition beanDefinition = new BeanDefinition(clazz);
        // 是否懒加载 (默认false)
        beanDefinition.setLazy(Optional.ofNullable(clazz.getAnnotation(Lazy.class)).map(Lazy::value).orElse(false));
        // 是否单例/原型 (默认单例)
        beanDefinition.setScope(Optional.ofNullable(clazz.getAnnotation(Scope.class)).map(Scope::value).orElse(ScopeEnum.SINGLE));
        beanDefinitionMap.put(beanName, beanDefinition);
    }


    private Object crateBean(String beanName, boolean immediately) {
        Object instance = null;
        try {

            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            ScopeEnum scope = beanDefinition.getScope();

            // 懒加载
            if (!immediately && beanDefinition.isLazy()) {
                return instance;
            }

            // bean单例且存在
            if (scope == ScopeEnum.SINGLE && singleBeanMap.containsKey(beanName)) {
                return singleBeanMap.get(beanName);
            }

            Class<?> source = beanDefinition.getSource();
            Constructor<?> constructor = source.getConstructor();
            constructor.setAccessible(true);
            instance = constructor.newInstance();

            //前置处理器
            if (!(instance instanceof BeanPostProcessor)) {
                for (String processorName : beanPostProcessorNameList) {
                    BeanPostProcessor beanPostProcessor = (BeanPostProcessor) singleBeanMap.get(processorName);
                    beanPostProcessor.postProcessBeforeInitialization(instance);
                }
            }

            // Autowired属性自动注入
            for (Field field : source.getDeclaredFields()) {
                if (field.isAnnotationPresent(WyyAutowired.class)) {
                    Class<?> type = field.getType();
                    String fieldBeanName = WyyUtil.getBeanNameByClass(type.getSimpleName());
                    Object bean = singleBeanMap.get(fieldBeanName);
                    if (Objects.isNull(bean)) {
                        bean = crateBean(fieldBeanName, true);
                    }
                    field.setAccessible(true);
                    field.set(instance, bean);
                }
            }

            //后置处理器
            if (!(instance instanceof BeanPostProcessor)) {
                for (String processorName : beanPostProcessorNameList) {
                    BeanPostProcessor beanPostProcessor = (BeanPostProcessor) singleBeanMap.get(processorName);

                    Object proxyBean = beanPostProcessor.postProcessAfterInitialization(instance);
                    if (source.isAnnotationPresent(Aop.class) && beanPostProcessor instanceof AopBeanPostProcessor) {
                        //AOP后置处理器
                        instance = proxyBean;
                    }

                }
            }

            if (scope == ScopeEnum.SINGLE) {
                singleBeanMap.put(beanName, instance);
            }
            return instance;

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public <T> T getBean(String beanName, Class<T> clazz) {
        return (T) getBean(beanName);
    }

    public Object getBean(String beanName) {
        if (!beanDefinitionMap.containsKey(beanName)) {
            throw new RuntimeException("bean定义为空");
        }
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);

        if (beanDefinition.getScope().equals(ScopeEnum.SINGLE)) {
            Object instance = singleBeanMap.get(beanName);
            if (null == instance) {
                instance = crateBean(beanName, true);
            }
            return instance;
        } else {
            return crateBean(beanName, true);
        }
    }

    // 获取单例池，验证懒加载
    public Map<String, Object> getSingleMap() {
        return singleBeanMap;
    }
}
