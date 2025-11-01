package com.kenji.web_order.enums;

public enum Gender {
    M("Male"),
    F("Female"),
    O("Other");
    private final String description;

    Gender(String value){
        this.description = value;
    }

    public String getDescription() {
        return description;
    }
}
