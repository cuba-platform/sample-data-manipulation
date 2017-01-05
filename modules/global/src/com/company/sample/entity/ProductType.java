package com.company.sample.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;

public enum ProductType implements EnumClass<String> {

    FOOD("F"),
    CLOTHING("C"),
    TOYS("T");

    private String id;

    ProductType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ProductType fromId(String id) {
        for (ProductType at : ProductType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}