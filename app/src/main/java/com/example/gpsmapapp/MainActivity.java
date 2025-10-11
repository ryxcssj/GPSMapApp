package com.example.gpsmapapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Toast;

import com.example.gpsmapapp.mapa.cargarMapa;
import com.example.gpsmapapp.mapa.UbicacionActual;
import com.example.gpsmapapp.mapa.MostrarRuta;
import com.mapbox.mapboxsdk.maps.MapView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private MapView mapView;
    private int vecesDenegados = 0;
    private Button button;

    private final ActivityResultLauncher<String[]> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                boolean ubicacion = result.getOrDefault(android.Manifest.permission.ACCESS_FINE_LOCATION, false);
                boolean locacion = result.getOrDefault(android.Manifest.permission.ACCESS_COARSE_LOCATION, false);
                if (ubicacion && locacion) {
                    Toast.makeText(this, "Permisos concedidos", Toast.LENGTH_SHORT).show();
                } else {
                    vecesDenegados ++;
                    if (vecesDenegados >= 2) {
                        DialogAlertPermisoConfiguracion();
                    } else {
                        DialogAlertSolicitudDePermisos();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.ButtonBuscarUbicacion);
        mapView = findViewById(R.id.map);
        verifiacionPermisos();
        button.setOnClickListener(v -> {
            verifiacionPermisos();
        });
    }

    private void verifiacionPermisos() {
        try {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permisos concedidos", Toast.LENGTH_SHORT).show();
            } else {
                SolicitarPermisos();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error desconocido", Toast.LENGTH_SHORT).show();
        }
    }

    private void SolicitarPermisos() {
        if(vecesDenegados    >= 2){
            DialogAlertPermisoConfiguracion();
        }else {
            requestPermissionLauncher.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }
    }

    private void DialogAlertSolicitudDePermisos() {
        new android.app.AlertDialog.Builder(this)
                .setTitle("Permisos Denegados")
                .setMessage("La ubicacion y locacion son necesarios para el funcionamineto de la app.")
                .setPositiveButton("Aceptar", (dialog, which) -> {
                    SolicitarPermisos();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void DialogAlertPermisoConfiguracion() {
        new android.app.AlertDialog.Builder(this)
                .setTitle("Permisos necesaarios no obtenidos")
                .setMessage("Para hacer uso de las funciones, se redirigira a los permisos de la aplicación.")
                .setPositiveButton("Ir a ajustes", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                })
                .setNegativeButton("Cancelar", null)
                .show();
        Toast.makeText(this, "Permisos no concedidos", Toast.LENGTH_SHORT).show();
    }


}


