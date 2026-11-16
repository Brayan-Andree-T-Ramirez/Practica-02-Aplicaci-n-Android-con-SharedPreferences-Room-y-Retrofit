package com.upiiz.practica_02.ui;

import android.content.Intent;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    private Button btnIniciar, btnIrFavoritosMain, btnIrConfiguracion;
    private TextView tvSaludo, tvResumenPreferencias;
    private Preferencias preferencias;


    private static boolean arranqueInicialRealizado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferencias = new Preferencias(this);
        preferencias.aplicarTema();

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvSaludo = findViewById(R.id.tvSaludo);
        tvResumenPreferencias = findViewById(R.id.tvResumenPreferencias);
        btnIniciar = findViewById(R.id.btnIniciar);
        btnIrFavoritosMain = findViewById(R.id.btnIrFavoritosMain);
        btnIrConfiguracion = findViewById(R.id.btnIrConfiguracion);

        btnIniciar.setOnClickListener(v -> {
            preferencias.guardarUltimaPantalla("catalogo");
            startActivity(new Intent(this, Catalogo.class));
        });

        btnIrFavoritosMain.setOnClickListener(v -> {
            preferencias.guardarUltimaPantalla("favoritos");
            startActivity(new Intent(this, Favoritos.class));
        });

        btnIrConfiguracion.setOnClickListener(v -> {
            startActivity(new Intent(this, Perfil.class));
        });

        if (!arranqueInicialRealizado) {
            arranqueInicialRealizado = true;
            String usuario = preferencias.obtenerUsuario();
            String ultima = preferencias.obtenerUtimaPantalla();

            if (usuario != null && !usuario.isEmpty()) {
                if ("favoritos".equalsIgnoreCase(ultima)) {
                    Toast.makeText(this, " Retomando última vista: Favoritos", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, Favoritos.class));
                } else if ("catalogo".equalsIgnoreCase(ultima)) {
                    Toast.makeText(this, " Retomando última vista: Catálogo", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, Catalogo.class));
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarInterfazPreferencias();
    }

    private void actualizarInterfazPreferencias() {
        String usuario = preferencias.obtenerUsuario();
        if (usuario == null || usuario.trim().isEmpty()) {
            tvSaludo.setText("¡Hola, lector!");
        } else {
            tvSaludo.setText("¡Hola, " + usuario + "!");
        }

        String tema = "oscuro".equalsIgnoreCase(preferencias.obtenerTema()) ? "Oscuro 🌙" : "Claro ☀️";
        String ultima = "favoritos".equalsIgnoreCase(preferencias.obtenerUtimaPantalla()) ? "Favoritos ⭐" : "Catálogo 🌐";

        tvResumenPreferencias.setText("Tema: " + tema + "  •  Última sección: " + ultima);
    }
}