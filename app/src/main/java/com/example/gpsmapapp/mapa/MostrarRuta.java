package com.example.gpsmapapp.mapa;

import android.content.Context;
import android.os.AsyncTask;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MostrarRuta {
    private final com.example.gpsmapapp.mapa.GoogleMap mMap;
    private final Context context;
    private static final String API_KEY = "AIzaSyCeFSAJW8R1hkRYK4Gdnc6HFn_Q2nG1oAg"; // Reemplaza con tu clave

    public MostrarRuta(Context context, GoogleMap map) {
        this.context = context;
        this.mMap = map;
    }

    public void drawRoute(LatLng origin, LatLng destination) {
        new FetchRouteTask().execute(origin, destination);
    }

    private class FetchRouteTask extends AsyncTask<LatLng, Void, String> {
        @Override
        protected String doInBackground(LatLng... params) {
            LatLng origin = params[0];
            LatLng dest = params[1];
            String url = "https://maps.googleapis.com/maps/api/directions/json?" +
                    "origin=" + origin.latitude + "," + origin.longitude +
                    "&destination=" + dest.latitude + "," + dest.longitude +
                    "&key=" + API_KEY;
            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    mMap.addPolyline(new PolylineOptions().add(new LatLng(0, 0), new LatLng(0, 0))); // Placeholder
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}