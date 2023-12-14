package com.cupme.domain.enumeration;

public enum SexType {
    HOMME("HOMME"),
    FEMME("FEMME");

    private String value;

    private SexType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
