package com.example.projectkm.ui.gallery;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.projectkm.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class SecondFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap map;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_second, container, false);

        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map));

        mapFragment.getMapAsync(this);
        return mView ;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        this.map = googleMap;
        this.map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        this.map.getUiSettings().setZoomControlsEnabled(true);
        this.map.getUiSettings().setCompassEnabled(true);
        this.map.getUiSettings().setMyLocationButtonEnabled(true);
        this.map.getUiSettings().setZoomGesturesEnabled(true);
        this.map.getUiSettings().setRotateGesturesEnabled(true);
        Bundle bundle = this.getArguments();
        if(bundle!=null) {
            String s = bundle.getString("addressmap");

            List<Address> addressList = null;
            if(s!=null){
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                Geocoder geocoder = new Geocoder(getActivity());
                try {
                    addressList=geocoder.getFromLocationName(s, 1);
                }catch (IOException e){
                    e.printStackTrace();
                }
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                map.addMarker(new MarkerOptions().position(latLng).title(s));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
            }
        }
    }

}
