package com.example.keepsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class VehicleForm extends AppCompatActivity {

    private Spinner spinner1;
    String usuario,email,contraseña;
    EditText licConduccion,licTransito,tecnomecanica,soat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_form);

        // Recuperación de los datos de la pantalla anterior.

        usuario = (String) getIntent().getExtras().get("usuario");
        email = (String) getIntent().getExtras().get("email");
        contraseña = (String) getIntent().getExtras().get("contraseña");

        licConduccion = (EditText)findViewById(R.id.editTextTextLicense);
        licTransito = (EditText)findViewById(R.id.editTextTranLicence);
        tecnomecanica = (EditText)findViewById(R.id.editTextTecnoMecDate);
        soat = (EditText)findViewById(R.id.editTextTecnoMecDate2);


        spinner1 = (Spinner)findViewById(R.id.spinner);
        String[] options = {"Seleccione su vehículo", "Automovil", "Moto"};
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_vehiculos, options);
        spinner1.setAdapter(adapter);

        Button botonEnviar = (Button)findViewById(R.id.buttonLogin);
        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!licConduccion.getText().toString().isEmpty() && !licTransito.getText().toString().isEmpty() && !tecnomecanica.getText().toString().isEmpty() && !soat.getText().toString().isEmpty() && !spinner1.getSelectedItem().toString().equals("Seleccione su vehículo")){
                    ejecutarPeticion("https://keepsafegestor.000webhostapp.com/registrar.php");
                }else{
                    Toast.makeText(getApplicationContext(), "Llene todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // Método para ejecutar petición de registro de datos en el webservice.

    private void ejecutarPeticion(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Datos registrados con exito!", Toast.LENGTH_SHORT).show();
                guardarPreferencias();
                Intent siguientePantalla = new Intent(VehicleForm.this,IniciarViaje.class);
                startActivity(siguientePantalla);
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
                parametros.put("registroEmail",email);
                parametros.put("registroUsuario",usuario);
                parametros.put("registroContraseña",contraseña);
                parametros.put("registroLicConduccion",licConduccion.getText().toString());
                parametros.put("registroLicTransito",licTransito.getText().toString());
                parametros.put("registroTecnomecanica",tecnomecanica.getText().toString());
                parametros.put("registroSoat",soat.getText().toString());
                parametros.put("registroVehiculo",spinner1.getSelectedItem().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Se agregan politicas de reintento para que no se cancele la conexión por tardanza.
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(15000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);
    }

    private void guardarPreferencias(){
        SharedPreferences preferencias = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String username = usuario;
        String password = contraseña;

        SharedPreferences.Editor editorPreferencias = preferencias.edit();
        editorPreferencias.putString("usuario",username);
        editorPreferencias.putString("contraseña",password);

        editorPreferencias.commit();
    }
}