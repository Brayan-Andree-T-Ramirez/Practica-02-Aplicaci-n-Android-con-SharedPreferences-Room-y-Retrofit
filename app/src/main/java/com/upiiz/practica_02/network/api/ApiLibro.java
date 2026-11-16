package com.upiiz.practica_02.network.api;

import com.upiiz.practica_02.models.Libro;
import com.upiiz.practica_02.models.RespuestaLibros;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiLibro {


    //Solo requiero dos endpoinds
    @GET("/api/v1/libros")
    Call<RespuestaLibros> obtenerLibros();

    @GET("/api/v1/libros/{id}")
    Call<Libro> obtenerLibro(@Path("id") Long id);
}
