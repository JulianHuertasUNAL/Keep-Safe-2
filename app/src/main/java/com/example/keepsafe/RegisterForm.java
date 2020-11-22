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
                //Intent siguientePantalla = new Intent(RegisterForm.this,VehicleForm.class);
                //startActivity(siguientePantalla);
                if(!usuario.getText().toString().isEmpty() | !email.getText().toString().isEmpty() | !contraseña.getText().toString().isEmpty() | !contraseñaRepeat.getText().toString().isEmpty()){
                    ejecutarPeticion("https://keepsafegestor.000webhostapp.com/registrar.php");
                    Toast.makeText(getApplicationContext(), "Datos registrados con exito!", Toast.LENGTH_SHORT).show();
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
                Log.d("MySQLConnection", "Datos registrados exitosamente!!");
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.d("MySQLConnection","[ERROR] "+ error.toString());
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("registroEmail",email.getText().toString());
                parametros.put("registroUsuario",usuario.getText().toString());
                parametros.put("registroContraseña",contraseña.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Se agregan politicas de reintento para que no se cancele la conexión por tardanza.
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(15000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);
    }
}