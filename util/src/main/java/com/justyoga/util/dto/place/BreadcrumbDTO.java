package com.justyoga.util.dto.place;

import java.io.Serializable;

public class BreadcrumbDTO implements Serializable {
    private String text;
    private boolean disabled;
    private String to;

    public BreadcrumbDTO() {}

    public BreadcrumbDTO(String text, boolean disabled, String to) {
        this.text = text;
        this.disabled = disabled;
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
