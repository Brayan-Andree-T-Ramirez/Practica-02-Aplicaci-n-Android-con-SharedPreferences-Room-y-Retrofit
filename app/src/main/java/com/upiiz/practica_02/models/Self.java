package com.upiiz.practica_02.models;

import java.io.Serializable;

public class Self implements Serializable {
    private String href;

    public Self(String href) {
        this.href = href;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
