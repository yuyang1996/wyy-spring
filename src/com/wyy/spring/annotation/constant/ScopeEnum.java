package com.wyy.spring.annotation.constant;

/**
 * @Author wyy
 * @Date 2022/02/24
 */
public enum ScopeEnum {
    SINGLE("εδΎ"),
    PROTOTYPE("εε");

    private String desc;

    ScopeEnum(String desc) {
        this.desc = desc;
    }
}
