package com.upiiz.practica_02.models;

public class RespuestaLibros {

    private Link _links;
    private Embedded _embedded;

    public Link get_links() {
        return _links;
    }

    public void set_links(Link _links) {
        this._links = _links;
    }

    public Embedded get_embedded() {
        return _embedded;
    }

    public void set_embedded(Embedded _embedded) {
        this._embedded = _embedded;
    }
}

