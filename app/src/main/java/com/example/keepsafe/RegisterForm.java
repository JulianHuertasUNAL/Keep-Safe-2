package com.example.keepsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterForm extends AppCompatActivity {

    EditText usuario, email, contraseña, contraseñaRepeat;
    Button botonRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        usuario = (EditText)findViewById(R.id.editTextTextEmailAddress);
        email = (EditText)findViewById(R.id.editTextTextEmailAddress2);
        contraseña = (EditText)findViewById(R.id.editTextPassword);
        contraseñaRepeat = (EditText)findViewById(R.id.editTextPassword2);
        botonRegistrar = (Button)findViewById(R.id.buttonRegister);

        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!usuario.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && !contraseña.getText().toString().isEmpty() && !contraseñaRepeat.getText().toString().isEmpty()){
                    Intent siguientePantalla = new Intent(RegisterForm.this,VehicleForm.class);
                    siguientePantalla.putExtra("usuario",usuario.getText().toString());
                    siguientePantalla.putExtra("email",email.getText().toString());
                    siguientePantalla.putExtra("contraseña",contraseña.getText().toString());
                    siguientePantalla.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(siguientePantalla);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Llene todos los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}