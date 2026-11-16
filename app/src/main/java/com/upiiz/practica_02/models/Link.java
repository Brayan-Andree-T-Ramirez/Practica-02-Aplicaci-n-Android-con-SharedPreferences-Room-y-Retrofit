package com.upiiz.practica_02.models;

import java.io.Serializable;

public class Link implements Serializable {
    private Self self;

    public Self getSelf() {
        return self;
    }

    public void setSelf(Self self) {
        this.self = self;
    }
}
