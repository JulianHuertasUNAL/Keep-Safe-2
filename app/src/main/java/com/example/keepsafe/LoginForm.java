package com.example.keepsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

        Button botonLoggear = (Button)findViewById(R.id.buttonLogin);

        botonLoggear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent siguientePantalla = new Intent(LoginForm.this,IniciarViaje.class);
                startActivity(siguientePantalla);
            }
        });

    }
}