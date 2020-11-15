package com.example.keepsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class VehicleForm extends AppCompatActivity {

    private Spinner spinner1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_form);

        spinner1 = (Spinner)findViewById(R.id.spinner);

        String[] options = {"Seleccione su vehículo", "Automovil", "Moto"};

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_vehiculos, options);
        spinner1.setAdapter(adapter);
    }
}