package com.example.keepsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Ajustes extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        Button perfil = (Button)findViewById(R.id.perfil);
        Button viaje = (Button)findViewById(R.id.play);
        Button logout = (Button)findViewById(R.id.buttonLogout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Reiniciamos las preferencias de usuario para no auto-logear la proxima vez.

                SharedPreferences preferencias = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

                SharedPreferences.Editor editorPreferencias = preferencias.edit();
                editorPreferencias.putString("usuario","noneUser");
                editorPreferencias.putString("contraseña","nonePassword");

                editorPreferencias.commit();

                Intent siguientePantalla = new Intent(Ajustes.this,LoginForm.class);
                siguientePantalla.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(siguientePantalla);
                finish();

            }
        });

        viaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viaje = new Intent(Ajustes.this,IniciarViaje.class);
                startActivity(viaje.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent perfil = new Intent(Ajustes.this,Perfil.class);
                startActivity(perfil.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }
}