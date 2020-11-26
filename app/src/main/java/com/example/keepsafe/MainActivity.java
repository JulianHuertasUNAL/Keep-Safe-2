package com.example.keepsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    String usuario, contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    cargarPreferencias();
                }
        }, 2000);



    }

    private void cargarPreferencias(){

        SharedPreferences preferencias = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String usernamePreferences = preferencias.getString("usuario","noneUser");
        String passwordPreferences = preferencias.getString("contraseña","nonePassword");

        if(!usernamePreferences.equals("noneUser") && !passwordPreferences.equals("nonePassword")){
            usuario = usernamePreferences;
            contraseña = passwordPreferences;

            ejecutarPeticion("https://keepsafegestor.000webhostapp.com/logear.php");



        }else{
            Intent pantallaInicial = new Intent(MainActivity.this, pantallaInicial.class);
            startActivity(pantallaInicial);
        }
    }

    // Método para ejecutar petición de inicio de sesión en el webservice.

    private void ejecutarPeticion(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("1")){
                    Toast.makeText(getApplicationContext(), "Login Exitoso", Toast.LENGTH_SHORT).show();
                    Intent siguientePantalla = new Intent(MainActivity.this,IniciarViaje.class);
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
                parametros.put("inicioUsuario",usuario);
                parametros.put("inicioContraseña",contraseña);
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Se agregan politicas de reintento para que no se cancele la conexión por tardanza.
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(15000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);
    }
}