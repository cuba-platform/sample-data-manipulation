/*
 * Copyright (c) 2016 data-manipulation
 */
package com.company.sample.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

/**
 * @author krivopustov
 */
public enum CustomerGrade implements EnumClass<Integer>{

    BRONZE(10),
    GOLD(20),
    PLATINUM(30);

    private Integer id;

    CustomerGrade (Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    public static CustomerGrade fromId(Integer id) {
        for (CustomerGrade at : CustomerGrade.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}