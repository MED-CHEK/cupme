package com.cupme.domain.enumeration;

public enum ProtocolType {
    SIMPLE("simple"),
    GENERIC("generic");

    private String value;

    private ProtocolType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
