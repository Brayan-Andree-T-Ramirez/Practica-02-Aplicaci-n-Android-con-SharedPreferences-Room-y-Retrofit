package com.upiiz.practica_02.data.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDelegate;

public class Preferencias {

    private static final String PREFS = "preferencias_app";
    private static final String USUARIO = "usuario";
    private static final String ULTIMA_PANTALLA = "ultima_pantalla";
    private static final String TEMA = "tema";

    private final SharedPreferences preferences;

    public Preferencias(Context context) {
        preferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public void guardarUsuario(String usuario) {
        preferences.edit().putString(USUARIO, usuario).apply();
    }

    public String obtenerUsuario() {
        return preferences.getString(USUARIO, "");
    }

    public void guardarUltimaPantalla(String pantalla) {
        preferences.edit().putString(ULTIMA_PANTALLA, pantalla).apply();
    }

    public String obtenerUtimaPantalla() {
        return preferences.getString(ULTIMA_PANTALLA, "catalogo");
    }

    public void guardarTema(String tema) {
        preferences.edit().putString(TEMA, tema).apply();
        aplicarTema();
    }

    public String obtenerTema() {
        return preferences.getString(TEMA, "claro");
    }

    public void aplicarTema() {
        if ("oscuro".equalsIgnoreCase(obtenerTema())) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}