package com.upiiz.practica_02.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.upiiz.practica_02.R;
import com.upiiz.practica_02.data.SharedPreferences.Preferencias;
import com.upiiz.practica_02.data.db.CrudLibro;
import com.upiiz.practica_02.data.entities.LibroEntity;
import com.upiiz.practica_02.models.Libro;
import com.upiiz.practica_02.network.LibroRetrofit;
import com.upiiz.practica_02.network.api.ApiLibro;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleLibro extends AppCompatActivity implements View.OnClickListener {

    private Button btnAgregarFavorito, btnEliminarFavoritos;
    private CrudLibro crudLibro;
    private TextView tvId, tvTitulo, tvAutor, tvEditorial, tvPaginas, tvDisponible, tvLinks;
    private ApiLibro apiLibro;
    private Libro libroActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Preferencias preferencias = new Preferencias(this);
        preferencias.aplicarTema();

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_libro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvId = findViewById(R.id.tvDetalleId);
        tvTitulo = findViewById(R.id.tvDetalleTitulo);
        tvAutor = findViewById(R.id.tvDetalleAutor);
        tvEditorial = findViewById(R.id.tvDetalleEditorial);
        tvPaginas = findViewById(R.id.tvDetallePaginas);
        tvDisponible = findViewById(R.id.tvDetalleDisponible);
        tvLinks = findViewById(R.id.tvLinksInfo);

        btnAgregarFavorito = findViewById(R.id.btnAgregarFavoritos);
        btnEliminarFavoritos = findViewById(R.id.btnEliminarFavoritos);

        crudLibro = new CrudLibro(this);
        apiLibro = LibroRetrofit.obtenerApiLibro();

        btnAgregarFavorito.setOnClickListener(this);
        btnEliminarFavoritos.setOnClickListener(this);

        if (getIntent().hasExtra("libro_data")) {
            libroActual = (Libro) getIntent().getSerializableExtra("libro_data");
            if (libroActual != null && libroActual.getId() != null) {
                cargarLibro(libroActual);
                consultarLibroActualizado(libroActual.getId());
                crudLibro.obtenerPorId(libroActual.getId(), libroEntity -> {
                    if (libroEntity != null) {
                        btnEliminarFavoritos.setVisibility(View.VISIBLE);
                        btnAgregarFavorito.setVisibility(View.GONE);
                    } else {
                        btnEliminarFavoritos.setVisibility(View.GONE);
                        btnAgregarFavorito.setVisibility(View.VISIBLE);
                    }
                });
            }
        } else {
            libroActual = (Libro) getIntent().getSerializableExtra("libro_data_offline");
            if (libroActual != null && libroActual.getId() != null) {
                cargarLibro(libroActual);
                btnEliminarFavoritos.setVisibility(View.VISIBLE);
                btnAgregarFavorito.setVisibility(View.GONE);
            }
        }
    }

    private void consultarLibroActualizado(Long id) {
        apiLibro.obtenerLibro(id).enqueue(new Callback<Libro>() {
            @Override
            public void onResponse(Call<Libro> call, Response<Libro> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getId() != null) {
                    libroActual = response.body();
                    cargarLibro(libroActual);
                }
            }

            @Override
            public void onFailure(Call<Libro> call, Throwable t) {
                Toast.makeText(DetalleLibro.this, ":( Sin red: Mostrando datos almacenados en caché", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarLibro(Libro librito) {
        tvId.setText("ID Libro: #" + librito.getId());
        tvTitulo.setText(librito.getTitulo());
        tvAutor.setText("Autor: " + librito.getAutor());
        tvEditorial.setText("Editorial: " + librito.getEditorial());
        tvPaginas.setText("Páginas: " + librito.getPaginas());

        if (librito.isDisponible()) {
            tvDisponible.setText("Disponible");
        } else {
            tvDisponible.setText("No disponible");
        }

        if (librito.getLinks() != null && librito.getLinks().getSelf() != null) {
            String linksUrl = "Links HATEOAS \n" + librito.getLinks().getSelf().getHref() + "\n"
                    + librito.getLinks().getLibros().getHref() + "\n"
                    + librito.getLinks().getActualizar().getHref() + "\n"
                    + librito.getLinks().getEliminar().getHref();
            tvLinks.setText(linksUrl);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAgregarFavoritos) {
            LibroEntity libro = new LibroEntity(
                    libroActual.getId(),
                    libroActual.getTitulo(),
                    libroActual.getAutor(),
                    libroActual.getEditorial(),
                    libroActual.getPaginas(),
                    libroActual.isDisponible(),
                    String.valueOf(tvLinks.getText())
            );
            crudLibro.agregar(libro);
            Toast.makeText(this, "⭐ Libro guardado en Favoritos (Offline)", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            crudLibro.eliminar(libroActual.getId());
            Toast.makeText(this, "🗑️ Libro eliminado de Favoritos", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}