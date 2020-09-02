package com.example.buidemapp;

import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

//implements LocationListener
public class fragment_Mapas extends Fragment {
    MapView mMapView;
    static GoogleMap googleMap;
    static Marker[] llistaLlocs;
    LocationManager locationManager;
    android.location.LocationListener locationListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mapa, container, false);
        llistaLlocs= new Marker[11];
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        /*
        locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener(){

            @Override
            public void onLocationChanged(Location location) {
                Barcode.GeoPoint userLoc = processNewLocation(location);
                if(userLoc != null){
                    Log.d("USERLOC", userLoc.toString());
                    //do something with the location
                }
            }

            @Override
            public void onProviderDisabled(String provider) {
                locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
                if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                } else {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                }
            }

            @Override
            public void onProviderEnabled(String provider) {
                locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
                if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                } else {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // TODO Auto-generated method stub

            }
        };
        */

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {

                googleMap = mMap;

            // For dropping a marker at a point on the Map
                LatLng mercatantoni = new LatLng(41.378637, 2.162035);

            googleMap.clear();

           /* llistaLlocs[10] = googleMap.addMarker(new MarkerOptions()
                    .position(mercatantoni)
                    .title("Mercat Sant Antoni")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.green_marker_m)));

            llistaLlocs[10].setTag(0);*/

            // For zooming automatically to the location of the marker
            // /*CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();*/
          //  CameraPosition cameraPositionMERCAT =  new CameraPosition.Builder().target(mercatantoni).zoom(15).build();
          //  googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPositionMERCAT));

            }
        });
        return rootView;
    }

    public void addMarcador(){}

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}