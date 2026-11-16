package com.upiiz.practica_02.models;

import java.util.List;

public class Embedded {
    private List<Libro> libroDtoList;

    public List<Libro> getLibroDtoList() {
        return libroDtoList;
    }

    public void setLibroDtoList(List<Libro> libroDtoList) {
        this.libroDtoList = libroDtoList;
    }
}
