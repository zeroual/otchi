package com.otchi.api.facades.dto;


public abstract class AbstractPostContent {

    protected String type;

    protected AbstractPostContent(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
