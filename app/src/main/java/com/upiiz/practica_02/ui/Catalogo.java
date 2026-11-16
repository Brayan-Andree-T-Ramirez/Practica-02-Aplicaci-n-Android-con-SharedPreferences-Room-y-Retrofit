package com.upiiz.practica_02.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.upiiz.practica_02.R;
import com.upiiz.practica_02.adapters.LibroAdapter;
import com.upiiz.practica_02.data.SharedPreferences.Preferencias;
import com.upiiz.practica_02.models.Embedded;
import com.upiiz.practica_02.models.Libro;
import com.upiiz.practica_02.models.RespuestaLibros;
import com.upiiz.practica_02.network.LibroRetrofit;
import com.upiiz.practica_02.network.api.ApiLibro;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Catalogo extends AppCompatActivity {

    private RecyclerView rvLibros;
    private ProgressBar progressBar;
    private FloatingActionButton fabFavoritos;
    private Button btnRefrescarCatalogo, btnIrPerfil;
    private TextView tvSubtituloUsuarioCatalogo, tvEstadoErrorCatalogo;
    private LibroAdapter adapter;
    private ApiLibro apiLibro;
    private Preferencias preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferencias = new Preferencias(this);
        preferencias.aplicarTema();

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_catalogo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Guardar que esta es la última sección visitada
        preferencias.guardarUltimaPantalla("catalogo");

        rvLibros = findViewById(R.id.rvLibros);
        progressBar = findViewById(R.id.progressBarMain);
        fabFavoritos = findViewById(R.id.fabFavoritos);
        btnRefrescarCatalogo = findViewById(R.id.btnRefrescarCatalogo);
        btnIrPerfil = findViewById(R.id.btnIrPerfil);
        tvSubtituloUsuarioCatalogo = findViewById(R.id.tvSubtituloUsuarioCatalogo);
        tvEstadoErrorCatalogo = findViewById(R.id.tvEstadoErrorCatalogo);

        rvLibros.setLayoutManager(new LinearLayoutManager(this));

        adapter = new LibroAdapter(libroSeleccionado -> {
            Intent intent = new Intent(Catalogo.this, DetalleLibro.class);
            intent.putExtra("libro_data", libroSeleccionado);
            startActivity(intent);
        });
        rvLibros.setAdapter(adapter);

        fabFavoritos.setOnClickListener(v -> {
            Intent intent = new Intent(this, Favoritos.class);
            startActivity(intent);
        });

        if (btnRefrescarCatalogo != null) {
            btnRefrescarCatalogo.setOnClickListener(v -> {
                Toast.makeText(this, " Actualizando catálogo...", Toast.LENGTH_SHORT).show();
                obtenerLibros();
            });
        }

        if (btnIrPerfil != null) {
            btnIrPerfil.setOnClickListener(v -> {
                startActivity(new Intent(this, Perfil.class));
            });
        }

        apiLibro = LibroRetrofit.obtenerApiLibro();
    }

    @Override
    protected void onResume() {
        super.onResume();
        preferencias.guardarUltimaPantalla("catalogo");

        String usuario = preferencias.obtenerUsuario();
        if (tvSubtituloUsuarioCatalogo != null && !usuario.isEmpty()) {
            tvSubtituloUsuarioCatalogo.setText("Catálogo en línea para " + usuario);
        }
        obtenerLibros();
    }

    private void obtenerLibros() {
        progressBar.setVisibility(View.VISIBLE);
        if (tvEstadoErrorCatalogo != null) {
            tvEstadoErrorCatalogo.setVisibility(View.GONE);
        }

        apiLibro.obtenerLibros().enqueue(new Callback<RespuestaLibros>() {
            @Override
            public void onResponse(Call<RespuestaLibros> call, Response<RespuestaLibros> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && response.body().get_embedded() != null) {
                    Embedded embedded = response.body().get_embedded();
                    List<Libro> lista = embedded.getLibroDtoList();
                    adapter.setLibros(lista);
                    if (lista == null || lista.isEmpty()) {
                        Toast.makeText(Catalogo.this, "No hay libros registrados en el servidor", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (tvEstadoErrorCatalogo != null) {
                        tvEstadoErrorCatalogo.setText(":(\n\nEl servidor respondió con un error (Código " + response.code() + ").\nIntenta actualizar en unos momentos.");
                        tvEstadoErrorCatalogo.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(Catalogo.this, ":( Error del servidor: Código " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RespuestaLibros> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                adapter.setLibros(new ArrayList<>());
                if (tvEstadoErrorCatalogo != null) {
                    tvEstadoErrorCatalogo.setText(":(\n\n¡Sin conexión a Internet!\nNo pudimos conectar con el servidor, pero puedes consultar tus Favoritos en modo Offline tocando la estrella ⭐.");
                    tvEstadoErrorCatalogo.setVisibility(View.VISIBLE);
                }
                Toast.makeText(Catalogo.this, ":( Sin conexión a Internet. Revisa tu red o abre tus Favoritos Offline.", Toast.LENGTH_LONG).show();
            }
        });
    }
}