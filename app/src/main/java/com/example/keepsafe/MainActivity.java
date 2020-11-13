package com.example.keepsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button botonLogin = (Button)findViewById(R.id.botonLogin);
        Button botonRegistro = (Button)findViewById(R.id.botonRegister);

        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent siguientePantalla = new Intent(MainActivity.this,LoginForm.class);
                startActivity(siguientePantalla);
            }
        });

        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent siguientePantalla = new Intent(MainActivity.this,RegisterForm.class);
                startActivity(siguientePantalla);
            }
        });
    }
}