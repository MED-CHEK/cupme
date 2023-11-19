package com.cupme.domain.enumeration;

public enum AdressType {
    BILLING("BILLING"),
    SHIPPING("SHIPPING");

    private String value;

    private AdressType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
