package com.upiiz.practica_02.network;

import com.upiiz.practica_02.network.api.ApiLibro;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LibroRetrofit {

    private static final String URL_BASE="https://ejercicio-08-documentacion-y-endpoints.onrender.com/";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static ApiLibro obtenerApiLibro(){
        return retrofit.create(ApiLibro.class);
    }
}
