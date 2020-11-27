package com.example.keepsafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class pantallaInicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicial);

        Button botonLogin = (Button) findViewById(R.id.botonLogin);
        Button botonRegistro = (Button) findViewById(R.id.botonRegister);

        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent siguientePantalla = new Intent(pantallaInicial.this, LoginForm.class);
                startActivity(siguientePantalla.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent siguientePantalla = new Intent(pantallaInicial.this, RegisterForm.class);
                startActivity(siguientePantalla.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }
}
