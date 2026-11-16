package com.upiiz.practica_02.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.upiiz.practica_02.R;
import com.upiiz.practica_02.adapters.LibroAdapter;
import com.upiiz.practica_02.data.SharedPreferences.Preferencias;
import com.upiiz.practica_02.data.db.CrudLibro;
import com.upiiz.practica_02.data.entities.LibroEntity;
import com.upiiz.practica_02.models.Libro;
import com.upiiz.practica_02.models.LinkLibroRespuesta;
import com.upiiz.practica_02.models.Self;

import java.util.ArrayList;
import java.util.List;

public class Favoritos extends AppCompatActivity {

    private RecyclerView rvLibrosOffline;
    private ProgressBar progressBar;
    private TextView tvSinFavoritos;
    private Button btnIrCatalogoDesdeFav;
    private LibroAdapter adapter;
    private CrudLibro crudLibro;
    private Preferencias preferencias;

    private List<Libro> listaLibrosObjeto;
    private List<LibroEntity> listaLibros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferencias = new Preferencias(this);
        preferencias.aplicarTema();

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favoritos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        preferencias.guardarUltimaPantalla("favoritos");

        crudLibro = new CrudLibro(this);

        rvLibrosOffline = findViewById(R.id.rvLibrosOffline);
        progressBar = findViewById(R.id.progressBarMain);
        tvSinFavoritos = findViewById(R.id.tvSinFavoritos);
        btnIrCatalogoDesdeFav = findViewById(R.id.btnIrCatalogoDesdeFav);

        rvLibrosOffline.setLayoutManager(new LinearLayoutManager(this));

        adapter = new LibroAdapter(libroSeleccionado -> {
            Intent intent = new Intent(this, DetalleLibro.class);
            intent.putExtra("libro_data_offline", libroSeleccionado);
            startActivity(intent);
        });

        rvLibrosOffline.setAdapter(adapter);

        if (btnIrCatalogoDesdeFav != null) {
            btnIrCatalogoDesdeFav.setOnClickListener(v -> {
                startActivity(new Intent(this, Catalogo.class));
                finish();
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        preferencias.guardarUltimaPantalla("favoritos");
        obtenerLibrosOffline();
    }

    private void obtenerLibrosOffline() {
        crudLibro.obtenerTodos(libros -> {
            listaLibros = new ArrayList<>(libros);
            listaLibrosObjeto = new ArrayList<>();

            listaLibros.forEach(libro -> {
                LinkLibroRespuesta linksLibroRespuesta = null;
                if (libro.getLinks() != null) {
                    String[] links = libro.getLinks().split("\n");
                    if (links.length >= 5) {
                        Self selfSelf = new Self(links[1]);
                        Self selfLibros = new Self(links[2]);
                        Self selfActualizar = new Self(links[3]);
                        Self selfEliminar = new Self(links[4]);
                        linksLibroRespuesta = new LinkLibroRespuesta(selfSelf, selfLibros, selfActualizar, selfEliminar);
                    }
                }

                Libro libroObjeto = new Libro(
                        libro.getId(),
                        libro.getTitulo(),
                        libro.getAutor(),
                        libro.getEditorial(),
                        libro.getPaginas(),
                        libro.isDisponible(),
                        linksLibroRespuesta
                );
                listaLibrosObjeto.add(libroObjeto);
            });

            adapter.setLibros(listaLibrosObjeto);

            if (tvSinFavoritos != null) {
                if (listaLibrosObjeto.isEmpty()) {
                    tvSinFavoritos.setVisibility(View.VISIBLE);
                } else {
                    tvSinFavoritos.setVisibility(View.GONE);
                }
            }
        });
    }
}