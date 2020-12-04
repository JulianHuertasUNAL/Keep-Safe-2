package com.example.keepsafe;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class IniciarViaje extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

    private boolean viajeEnCurso = false;
    private Button perfil;
    private Button ajustes;
    private ImageButton botonCentral;
    private TextView txt;

    private FusedLocationProviderClient client;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private final static int LOCATION_PERMISSION = 124;

    private View.OnClickListener iniciarViaje;
    private View.OnClickListener pausarViaje;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_viaje);

        perfil = (Button) findViewById(R.id.perfil);
        ajustes = (Button) findViewById(R.id.configuracion);
        botonCentral = (ImageButton) findViewById(R.id.BotonCentral);
        txt = (TextView) findViewById(R.id.CentralText);

        initializeListeners();
        setListeners();

        mediaPlayer = MediaPlayer.create(this, R.raw.alarmatqm);
        client = LocationServices.getFusedLocationProviderClient(this);
        createLocationRequest();
        askForLocationPermission();

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                double speed = locationResult.getLastLocation().getSpeed() * 3.6;
                speed = Math.round(10 * speed) / 10.0;

                TextView speedDisplay = (TextView) findViewById(R.id.CentralText);
                speedDisplay.setText(String.valueOf(speed) + "km/h");

                if (speed > 1) {
                    mediaPlayer.start();
                }
            }
        };

    }

    private void initializeListeners() {
        iniciarViaje = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viajeEnCurso = true;

                if (ActivityCompat.checkSelfPermission(IniciarViaje.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(IniciarViaje.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                client.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

                botonCentral.setImageResource(R.drawable.media_pause);
                txt.setText("0.0 km/h");
                resetImageButtonListener();
            }
        };

        pausarViaje = new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                viajeEnCurso = false;
                client.removeLocationUpdates(locationCallback);
                botonCentral.setImageResource(R.drawable.media_play);
                txt.setText("Iniciar viaje");
                resetImageButtonListener();
            }
        };
    }

    private void resetImageButtonListener () {
        if (viajeEnCurso) {
            botonCentral.setOnClickListener(pausarViaje);
        } else {
            botonCentral.setOnClickListener(iniciarViaje);
        }
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        if (viajeEnCurso) {
            client.removeLocationUpdates(locationCallback);
        }
    }

    private void setListeners () {
        ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ajustes = new Intent(IniciarViaje.this,Ajustes.class);
                startActivity(ajustes.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent perfil = new Intent(IniciarViaje.this,Perfil.class);
                startActivity(perfil.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        botonCentral.setOnClickListener(iniciarViaje);
    }

    private void createLocationRequest () {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);
    }

    private void askForLocationPermission() {
        if (hasLocationPermission()) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    //DO nothing
                }
            });
        } else {
            EasyPermissions.requestPermissions(this, "Se necesita que de permiso para usar el GPS", LOCATION_PERMISSION, Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    private boolean hasLocationPermission() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            Toast.makeText(this, "OnActivityResult", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        //Intentionally empty
    }

    @Override
    public void onRationaleAccepted(int requestCode) {
        //Intentionally empty
    }

    @Override
    public void onRationaleDenied(int requestCode) {
        //Intentionally empty
    }


}