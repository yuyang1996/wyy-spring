package com.wyy.spring.common;

import com.wyy.spring.annotation.constant.ScopeEnum;

/**
 * @Author wyy
 * @Date 2022/02/24
 */
public class BeanDefinition {
    private Class<?> source;
    private ScopeEnum scope;
    private boolean isLazy;

    public BeanDefinition(Class<?> source) {
        this.source = source;
    }

    public BeanDefinition(Class<?> source, ScopeEnum scope, boolean isLazy) {
        this.source = source;
        this.scope = scope;
        this.isLazy = isLazy;
    }

    public Class<?> getSource() {
        return source;
    }

    public void setSource(Class<?> source) {
        this.source = source;
    }

    public ScopeEnum getScope() {
        return scope;
    }

    public void setScope(ScopeEnum scope) {
        this.scope = scope;
    }

    public boolean isLazy() {
        return isLazy;
    }

    public void setLazy(boolean lazy) {
        isLazy = lazy;
    }
}
