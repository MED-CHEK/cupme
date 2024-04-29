package com.cupme.domain.enumeration;

public enum MediaType {
    MAIL("mail"),
    WHATSAPP("whatsApp");

    private String value;

    private MediaType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
