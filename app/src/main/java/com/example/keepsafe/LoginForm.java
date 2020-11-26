package com.example.keepsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class LoginForm extends AppCompatActivity {

    EditText usuario, contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

        usuario = (EditText) findViewById(R.id.editTextTextEmailAddress);
        contraseña = (EditText) findViewById(R.id.editTextPassword);

        cargarPreferencias();

        Button botonLoggear = (Button)findViewById(R.id.buttonLogin);
        botonLoggear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!usuario.getText().toString().isEmpty() && !contraseña.getText().toString().isEmpty()){
                    ejecutarPeticion("https://keepsafegestor.000webhostapp.com/logear.php");
                }else{
                    Toast.makeText(getApplicationContext(), "Llene todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void cargarPreferencias(){

        SharedPreferences preferencias = getSharedPreferences("credenciales",Context.MODE_PRIVATE);

        String username = preferencias.getString("usuario","noneUser");
        String password = preferencias.getString("contraseña","nonePassword");

        if(!username.equals("noneUser") && !password.equals("nonePassword")){
            usuario.setText(username);
            contraseña.setText(password);

            ejecutarPeticion("https://keepsafegestor.000webhostapp.com/logear.php");
        }
    }
    
    private void guardarPreferencias(){
        SharedPreferences preferencias = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String username = usuario.getText().toString();
        String password = contraseña.getText().toString();

        SharedPreferences.Editor editorPreferencias = preferencias.edit();
        editorPreferencias.putString("usuario",username);
        editorPreferencias.putString("contraseña",password);

        editorPreferencias.commit();
    }

    // Método para ejecutar petición de inicio de sesión en el webservice.

    private void ejecutarPeticion(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("1")){
                    guardarPreferencias();
                    Toast.makeText(getApplicationContext(), "Login Exitoso", Toast.LENGTH_SHORT).show();
                    Intent siguientePantalla = new Intent(LoginForm.this,IniciarViaje.class);
                    startActivity(siguientePantalla);
                }else if(response.equals("0")){
                    Toast.makeText(getApplicationContext(), "Este usuario no existe", Toast.LENGTH_SHORT).show();
                }

            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.d("MySQLConnection","[ERROR] "+ error.toString());
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("inicioUsuario",usuario.getText().toString());
                parametros.put("inicioContraseña",contraseña.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Se agregan politicas de reintento para que no se cancele la conexión por tardanza.
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(15000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);
    }


}