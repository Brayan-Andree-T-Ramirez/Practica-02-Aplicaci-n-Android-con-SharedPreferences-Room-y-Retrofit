package com.upiiz.practica_02.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LinkLibroRespuesta implements Serializable {

    private Self self;
    @SerializedName("libros")
    //Sirve para indicarle el nombre de propiedad
    private Self libros;
    @SerializedName("actualizar")
    private Self actualizar;
    @SerializedName("eliminar")
    private Self eliminar;

    public LinkLibroRespuesta(Self self, Self libros, Self actualizar, Self eliminar) {
        this.self = self;
        this.libros = libros;
        this.actualizar = actualizar;
        this.eliminar = eliminar;
    }

    public Self getSelf() {
        return self;
    }

    public void setSelf(Self self) {
        this.self = self;
    }

    public Self getLibros() {
        return libros;
    }

    public void setLibros(Self libros) {
        this.libros = libros;
    }

    public Self getActualizar() {
        return actualizar;
    }

    public void setActualizar(Self actualizar) {
        this.actualizar = actualizar;
    }

    public Self getEliminar() {
        return eliminar;
    }

    public void setEliminar(Self eliminar) {
        this.eliminar = eliminar;
    }
}
