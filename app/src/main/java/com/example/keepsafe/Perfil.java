package com.example.keepsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Perfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Button ajustes = (Button)findViewById(R.id.configuracion);
        Button viaje = (Button)findViewById(R.id.play);

        ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ajustes = new Intent(Perfil.this,Ajustes.class);
                startActivity(ajustes);
            }
        });

        viaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viaje = new Intent(Perfil.this,IniciarViaje.class);
                startActivity(viaje);
            }
        });
    }
}