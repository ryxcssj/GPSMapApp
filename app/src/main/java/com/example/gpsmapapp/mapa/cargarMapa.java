package com.example.gpsmapapp.mapa;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;

public class cargarMapa implements OnMapReadyCallback {
    private GoogleMap Map;

    public cargarMapa(GoogleMap map) {
        this.Map = map;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Map = googleMap;
        UiSettings uiSettings = Map.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setCompassEnabled(true);
        Map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
}