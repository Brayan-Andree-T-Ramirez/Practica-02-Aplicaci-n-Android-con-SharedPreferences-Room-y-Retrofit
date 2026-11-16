package com.upiiz.practica_02.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Libro implements Serializable {
    private Long id;
    private String titulo;
    private String autor;
    private String editorial;
    private int paginas;
    private boolean disponible;

    public Libro(Long id, String titulo, String autor, String editorial, int paginas, boolean disponible, LinkLibroRespuesta links) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.paginas = paginas;
        this.disponible = disponible;
        this.links = links;
    }

    @SerializedName("_links")
    private LinkLibroRespuesta links;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getPaginas() {
        return paginas;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public LinkLibroRespuesta getLinks() {
        return links;
    }

    public void setLinks(LinkLibroRespuesta links) {
        this.links = links;
    }
}
