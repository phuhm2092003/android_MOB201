package fpt.edu.schoolproject.ui.map;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import fpt.edu.schoolproject.R;

public class BanDoFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ban_do, container, false);
        SupportMapFragment map = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        map.getMapAsync(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng fptDN = new LatLng(16.075872164343465, 108.16991681101761);
        mMap.addMarker(new MarkerOptions().position(fptDN).title("FPT Polytechnic Đà Nẵng"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(fptDN));
        LatLng fptHN = new LatLng(21.041488753245837, 105.745928186332);
        mMap.addMarker(new MarkerOptions().position(fptHN).title("FPT Polytechnic Hà Nội"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(fptHN));
        LatLng fptSG = new LatLng(10.81486075900871, 106.679869209532);
        mMap.addMarker(new MarkerOptions().position(fptSG).title("FPT Polytechnic Sài Gòn"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(fptSG));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15);
                marker.showInfoWindow();
                mMap.animateCamera(cameraUpdate, 500, null);
                return true;
            }
        });
    }
}