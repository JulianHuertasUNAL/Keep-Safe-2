package com.example.keepsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Perfil extends AppCompatActivity {

    String usuario;
    TextView userName,licConduccion;
    RequestQueue requestQueue;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Button ajustes = (Button)findViewById(R.id.configuracion);
        Button viaje = (Button)findViewById(R.id.play);

        userName = (TextView) findViewById(R.id.textView3);
        licConduccion = (TextView) findViewById(R.id.textView5);

        cargarPreferencias();
        recuperarDatosUsuario("https://keepsafegestor.000webhostapp.com/recuperarDatosUsuario.php?usuario='"+usuario+"'");

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

    private void cargarPreferencias(){

        SharedPreferences preferencias = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        usuario = preferencias.getString("usuario","noneUser");

    }

    private void recuperarDatosUsuario(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i<response.length();i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        userName.setText(jsonObject.getString("usuario"));
                        licConduccion.setText(jsonObject.getString("lic_conduccion"));
                    } catch (JSONException e) {
                        Log.d("JSONException", "[ERROR]" + e);
                    }
                    Log.d("JSONResponse", jsonObject.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VolleyError","[ERROR] "+error);
            }
        });

        requestQueue = Volley.newRequestQueue(this);
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(15000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);
    }
}