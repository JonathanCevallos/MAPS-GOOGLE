package com.example.maps_google;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.maps_google.Adapter.Adapter;
import com.google.android.gms.maps.SupportMapFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    String URL = "https://my-json-server.typicode.com/JonathanCevallos/GOOGLE-MAPS-MARKERS/db";
    ArrayList<Model> lista = new ArrayList<>();
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new Adapter(MainActivity.this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().
                findFragmentById(R.id.map);
        mapFragment.getMapAsync(adapter);
        requestQueue = Volley.newRequestQueue(this);
        stringRequest();
    }

    public void stringRequest() {
        StringRequest request = new StringRequest(Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray JSONlista = object.getJSONArray("facultades");
                            for (int i = 0; i < JSONlista.length(); i++) {
                                JSONObject data = JSONlista.getJSONObject(i);
                                Log.d("datos", data.toString());
                                lista.add(new Model(data.getString("nombre"),
                                        data.getString("direccion"),
                                        data.getString("decano"),
                                        data.getString("contacto"),
                                        data.getString("logo"),
                                        data.getDouble("latitud"),
                                        data.getDouble("longitud")));
                            }
                            adapter.marcadores(lista);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(final VolleyError error) {
                        if (error.networkResponse == null
                                && error instanceof NoConnectionError
                                && error.getMessage().contains("javax.net.ssl.SSLHandshakeException")) {
                        }
                    }
                }
        );
        requestQueue.add(request);
    }
}