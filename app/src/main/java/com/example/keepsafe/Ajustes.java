package com.example.keepsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        viaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viaje = new Intent(Ajustes.this,IniciarViaje.class);
                startActivity(viaje);
            }
        });

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent perfil = new Intent(Ajustes.this,Perfil.class);
                startActivity(perfil);
            }
        });
    }
}