package com.example.maps_google.Adapter;


import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.maps_google.Model;
import com.example.maps_google.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter implements OnMapReadyCallback, GoogleMap.InfoWindowAdapter {
    Context context;
    GoogleMap map;

    public Adapter(Context mContext) {
        context = mContext;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
        map.setInfoWindowAdapter(Adapter.this);
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        Toast toast = Toast.makeText(context, "ver carta", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        return null;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        View infoView = LayoutInflater.from(context).inflate(R.layout.item, null);
        TextView nombre = (TextView) infoView.findViewById(R.id.txtNombre);
        TextView decano = (TextView) infoView.findViewById(R.id.txtDecano);
        TextView contacto = (TextView) infoView.findViewById(R.id.txtContacto);
        TextView direccion = (TextView) infoView.findViewById(R.id.txtDireccion);
        TextView latitud = (TextView) infoView.findViewById(R.id.txtLatitud);
        TextView longitud = (TextView) infoView.findViewById(R.id.txtLongitud);
        ImageView logo = (ImageView) infoView.findViewById(R.id.imageLogo);

        Model model = (Model) marker.getTag();
        nombre.setText(model.getNombre());
        decano.setText(model.getDecano());
        contacto.setText(model.getContacto());
        direccion.setText(model.getDireccion());
        latitud.setText(model.getLatitud().toString());
        longitud.setText(model.getLongitud().toString());

        try{
            Picasso.with(context)
                    .load(model.getLogo())
                    .into(logo);
        }catch(Exception e){
            e.printStackTrace();
        }

        return infoView;
    }

    public void marcadores(ArrayList<Model> point) {
        for (int i = 0; i < point.size(); i++) {
            Model model = point.get(i);
            Marker marker = map.addMarker(new
                    MarkerOptions().position(new LatLng(model.getLatitud(), model.getLongitud()))
                    .title(model.getNombre())
                    .snippet(model.getDireccion()));
            marker.setTag(model);
        }

    }

}