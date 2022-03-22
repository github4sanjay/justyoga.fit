package com.justyoga.util.response;

public enum Status {
    SUCCESS("success"),
    ERROR("error");

    private String value;

    Status(String val) {
        this.value = val;
    }

    public String getValue() {
        return value;
    }
}
