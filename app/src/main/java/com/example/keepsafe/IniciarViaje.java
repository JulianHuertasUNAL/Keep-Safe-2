package com.example.keepsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class IniciarViaje extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_viaje);

        Button perfil = (Button)findViewById(R.id.perfil);
        Button ajustes = (Button)findViewById(R.id.configuracion);
        ImageButton iniciarViaje =(ImageButton)findViewById(R.id.imageButton3);
        ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ajustes = new Intent(IniciarViaje.this,Ajustes.class);
                startActivity(ajustes.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent perfil = new Intent(IniciarViaje.this,Perfil.class);
                startActivity(perfil.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        iniciarViaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iniciarViaje = new Intent(IniciarViaje.this,ViajeIniciado.class);
                startActivity(iniciarViaje.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });





    }
}