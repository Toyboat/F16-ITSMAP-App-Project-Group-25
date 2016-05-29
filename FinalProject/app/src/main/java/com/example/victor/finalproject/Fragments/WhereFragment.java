package com.example.victor.finalproject.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.victor.finalproject.MainActivity;
import com.example.victor.finalproject.ProjectConstants;
import com.example.victor.finalproject.R;
import com.example.victor.finalproject.WhatWhenWhereInterface;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link } interface
 * to handle interaction events.
 * Use the {@link WhereFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WhereFragment extends Fragment {

    private static final String moduleName = "WhereFragment";

    private WhatWhenWhereInterface fragmentInterface;
    private View view;
    private GoogleMap map;
    private double userLatitude;
    private double userLongitude;
    private double latitude, longitude;
    public static final String USER_LATITUDE = "location_latitude";
    public static final String USER_LONGITUDE = "location_longitude";
    private boolean userLocationKnown = false;
    private boolean tracing = true;
    private int mapType = GoogleMap.MAP_TYPE_NORMAL;
    private Location userLocation = null;
    private LocationManager locationManager;

    public WhereFragment() {
        Log.d(moduleName,"Constructor();");
        // Required empty public constructor
    }

    public static WhereFragment newInstance() {
        Log.d(moduleName,"newInstance");
        WhereFragment fragment = new WhereFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(moduleName,"onCreate();");
        super.onCreate(savedInstanceState);



        Intent data = getActivity().getIntent();
        if (data.hasExtra(USER_LONGITUDE) && data.hasExtra(USER_LATITUDE)) {

            userLatitude = data.getDoubleExtra(USER_LATITUDE, 0);
            userLongitude = data.getDoubleExtra(USER_LONGITUDE, 0);
            if (userLatitude != 0 && userLongitude != 0) {
                userLocationKnown = true;
            }
        }
        //zoomToUser();//gets location?
//        setUpMapIfNeeded();//what do?
        locationUpdateBroadcast(userLocation);
        Log.d("maps", "longandlat" + getLocation().toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(moduleName,"onCreateView();");
        View view;

        view = inflater.inflate(R.layout.fragment_where, container, false);

        return view;
    }

    @Override
    public void onResume() {
        Log.d(moduleName,"onResume();");
        super.onResume();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(locationUpdateReceiver, new IntentFilter(ProjectConstants.BroadcastLocationUpdateAction));
//        setUpMapIfNeeded();
    }

    @Override
    public void onPause() {
        Log.d(moduleName,"onPause();");
        super.onPause();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(locationUpdateReceiver);
    }


    @Override
    public void onAttach(Activity activity) {
        Log.d(moduleName,"onAttach();");
        super.onAttach(activity);
        try {
            fragmentInterface = (WhatWhenWhereInterface) activity;
        } catch (ClassCastException ex) {
            //Activity does not implement correct interface
            throw new ClassCastException(activity.toString() + " must implement WhatWhenWhereInterface");
        }
    }

    public void expand(String s) {
        Log.d(moduleName,"expand();");
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (s == "lost") {
            Log.d("pre.maps", "longandlat" + getLocation().toString());
            view = inflater.inflate(R.layout.where_lost_opened, null);
            //Location returnLocation = getLocation();
            Log.d("mapsexpander", "longandlat" + getLocation().toString());
        } else {
            view = inflater.inflate(R.layout.where_found_opened, null);
        }
        ViewGroup rootView = (ViewGroup) getView();
        rootView.removeAllViews();
        rootView.addView(view);
    }

    private Location getLocation() {
        Log.d(moduleName,"getLocation();");
        Location l = new Location("currentLocation");
        l.setLatitude(userLatitude);
        l.setLongitude(userLongitude);
        return l;
    }

    public void compress(String s) {
        Log.d(moduleName,"compress();");
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.fragment_where, null);
        ViewGroup rootView = (ViewGroup) getView();
        rootView.removeAllViews();
        rootView.addView(view);
    }

    @Override
    public void onDetach() {
        Log.d(moduleName,"onDetach();");
        super.onDetach();
    }


    public void requestData() {
        Log.d(moduleName,"requestData();");
    }

    //todo: return data
    public Location getUserLocation() {
        Log.d(moduleName,"getUserLocation();");
        return null;
    }

    private void setUpMapIfNeeded() {
        Log.d(moduleName,"setUpMapIfNeeded();");
        // Do a null check to confirm that we have not already instantiated the map.
        if (map == null) {
            // Try to obtain the map from the SupportMapFragment.
            //   map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (map != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        Log.d(moduleName,"setUpMap();");
        if (userLocationKnown) {

            if (!tracing) {
                map.clear();   //if we are not tracking, remove the marker first
            }
            map.addMarker(new MarkerOptions().position(new LatLng(userLatitude, userLongitude)).title("You are here!"));
        }
    }

    private void zoomToUser() {
        Log.d(moduleName,"zoomToUser();");
        if (userLocationKnown) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(userLatitude, userLongitude), 12));
        } else {
            Log.d("Error", "Unknown user location");
        }
    }

    private void locationUpdateBroadcast(Location location) {
        Log.d(moduleName,"locationUpdateBroadcast();");

        if (location != null) {
            Log.d(moduleName,"new Intent();");
            Intent update = new Intent(ProjectConstants.BroadcastLocationUpdateAction);
            Log.d(moduleName, "getLatitude();");
            update.putExtra(USER_LATITUDE, location.getLatitude());
            Log.d(moduleName, "getLongitude();");
            update.putExtra(USER_LONGITUDE, location.getLongitude());
            Log.d(moduleName,"sendBroadcast();");
            LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).sendBroadcast(update);
        }else
        {
            Log.d(moduleName,"location == null !!");
        }

    }

    BroadcastReceiver locationUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent data) {
            Log.d("locationUpdateReceiver","onReceive();");

            //Toast.makeText(getApplicationContext(), "Got location update", Toast.LENGTH_SHORT).show();

            if (data.hasExtra(USER_LONGITUDE) && data.hasExtra(USER_LATITUDE)) {

                userLatitude = data.getDoubleExtra(USER_LATITUDE, 0);
                userLongitude = data.getDoubleExtra(USER_LONGITUDE, 0);
                if (userLatitude != 0 && userLongitude != 0) {
                    userLocationKnown = true;
                }
            }
            setUpMap();
        }
    };

    private void updateStatus() {
        Log.d(moduleName,"updateStatus();");
        String status = "";
        if (userLocation != null) {
            status += " location=known";
            latitude = userLocation.getLatitude();
            longitude = userLocation.getLongitude();
            //txtPosition.setText("Lat: \t" + latitude + "\nLong: \t" + longitude);
        } else {
            //txtPosition.setText("Unknown\n");
            status += " location=unknown";

        }

        if (tracing) {
            status += " tracking=true";
        } else {
            status += " tracking=false";
        }

        if (userLocation == null && locationManager != null) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location lastGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location lastNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            status += " \nlast_known_location=";
            if(lastGps!=null){
                status += "{" + lastGps.getLatitude() + ";" + lastGps.getLongitude() + "}(GPS) ";
            }
            if(lastNetwork!=null){
                status += "{" + lastNetwork.getLatitude() + ";" + lastNetwork.getLongitude() + "}(Network) ";
            }
        }

        Log.d("updatate status:","Status:" + status);
    }
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.d("locationListener","onLocationChanged();");
            userLocation = location;
            updateStatus();
            locationUpdateBroadcast(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("locationListener","onStatusChanged();");
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d("locationListener","onProviderEnabled();");
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d("locationListener","onProviderDisabled();");
        }
    };
}
