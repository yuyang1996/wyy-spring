package com.wyy.spring.annotation.constant;

/**
 * @Author wyy
 * @Date 2022/02/24
 */
public enum ScopeEnum {
    SINGLE("单例"),
    PROTOTYPE("原型");

    private String desc;

    ScopeEnum(String desc) {
        this.desc = desc;
    }
}
