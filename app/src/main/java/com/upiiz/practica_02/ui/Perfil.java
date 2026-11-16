package com.upiiz.practica_02.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.upiiz.practica_02.R;
import com.upiiz.practica_02.data.SharedPreferences.Preferencias;

public class Perfil extends AppCompatActivity {

    private TextInputEditText etNombreUsuario;
    private RadioGroup rgTema;
    private RadioButton rbTemaClaro, rbTemaOscuro;
    private Button btnGuardarPreferencias;
    private Preferencias preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferencias = new Preferencias(this);
        preferencias.aplicarTema();

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etNombreUsuario = findViewById(R.id.etNombreUsuario);
        rgTema = findViewById(R.id.rgTema);
        rbTemaClaro = findViewById(R.id.rbTemaClaro);
        rbTemaOscuro = findViewById(R.id.rbTemaOscuro);
        btnGuardarPreferencias = findViewById(R.id.btnGuardarPreferencias);

        cargarDatosActuales();

        btnGuardarPreferencias.setOnClickListener(v -> guardarCambios());
    }

    private void cargarDatosActuales() {
        String nombreActual = preferencias.obtenerUsuario();
        if (!nombreActual.isEmpty()) {
            etNombreUsuario.setText(nombreActual);
        }

        String temaActual = preferencias.obtenerTema();
        if ("oscuro".equalsIgnoreCase(temaActual)) {
            rbTemaOscuro.setChecked(true);
        } else {
            rbTemaClaro.setChecked(true);
        }
    }

    private void guardarCambios() {
        String nombre = etNombreUsuario.getText() != null ? etNombreUsuario.getText().toString().trim() : "";

        if (nombre.isEmpty()) {
            etNombreUsuario.setError("Por favor ingresa tu nombre o alias");
            etNombreUsuario.requestFocus();
            return;
        }

        String temaSeleccionado = rbTemaOscuro.isChecked() ? "oscuro" : "claro";

        preferencias.guardarUsuario(nombre);
        preferencias.guardarTema(temaSeleccionado);

        Toast.makeText(this, "Preferencias guardadas correctamente :)", Toast.LENGTH_SHORT).show();
        finish();
    }
}