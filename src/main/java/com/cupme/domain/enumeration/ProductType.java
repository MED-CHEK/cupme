package com.cupme.domain.enumeration;

public enum ProductType {
    PRODUCT("product"),
    PROTOCOL("protocol");

    private String value;

    private ProductType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
