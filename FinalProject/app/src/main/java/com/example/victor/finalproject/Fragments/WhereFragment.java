package com.example.victor.finalproject.Fragments;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.victor.finalproject.MainActivity;
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
    private WhatWhenWhereInterface fragmentInterface;
    private View view;
    private GoogleMap map;
    private double userLatitude;
    private double userLongitude;
    public static final String USER_LATITUDE = "location_latitude";
    public static final String USER_LONGITUDE = "location_longitude";
    private boolean userLocationKnown = false;
    private boolean tracing = true;
    private int mapType = GoogleMap.MAP_TYPE_NORMAL;


    public WhereFragment() {
        // Required empty public constructor
    }

    public static WhereFragment newInstance() {
        WhereFragment fragment = new WhereFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent data = getActivity().getIntent();
        if(data.hasExtra(USER_LONGITUDE) && data.hasExtra(USER_LATITUDE)){

            userLatitude = data.getDoubleExtra(USER_LATITUDE, 0);
            userLongitude = data.getDoubleExtra(USER_LONGITUDE, 0);
            if(userLatitude!=0 && userLongitude!=0) {
                userLocationKnown = true;
            }
        }
        //zoomToUser();//gets location?
        //setUpMapIfNeeded();//what do?
        Log.d("maps","longandlat" + getLocation().toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_where, container, false);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragmentInterface = (WhatWhenWhereInterface) activity;
        } catch (ClassCastException ex) {
            //Activity does not implement correct interface
            throw new ClassCastException(activity.toString() + " must implement WhatWhenWhereInterface");
        }
    }

    public void expand(String s) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (s == "lost") {
            view = inflater.inflate(R.layout.where_lost_opened, null);
            //Location returnLocation = getLocation();
            Log.d("mapsexpander","longandlat" + getLocation().toString());
        } else {
            view = inflater.inflate(R.layout.where_found_opened, null);
        }
        ViewGroup rootView = (ViewGroup) getView();
        rootView.removeAllViews();
        rootView.addView(view);
    }

    private Location getLocation() {
        Location l = new Location("currentLocation");
        l.setLatitude(userLatitude);
        l.setLongitude(userLongitude);
        return l;
    }

    public void compress(String s) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.fragment_where, null);
        ViewGroup rootView = (ViewGroup) getView();
        rootView.removeAllViews();
        rootView.addView(view);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public void requestData() {
    }

    //todo: return data
    public Location getUserLocation() {
        return null;
    }
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map == null) {
            // Try to obtain the map from the SupportMapFragment.
            map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (map != null) {
                setUpMap();
            }
        }
    }
    private void setUpMap() {
        if(userLocationKnown) {

            if(!tracing){
                map.clear();   //if we are not tracking, remove the marker first
            }
            map.addMarker(new MarkerOptions().position(new LatLng(userLatitude, userLongitude)).title("You are here!"));
        }
    }

    private void zoomToUser(){
        if(userLocationKnown) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(userLatitude, userLongitude), 12));
        } else {
            Log.d("Error","User location unknown");
        }
    }

    BroadcastReceiver locationUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent data) {

            //Toast.makeText(getApplicationContext(), "Got location update", Toast.LENGTH_SHORT).show();

            if(data.hasExtra(USER_LONGITUDE) && data.hasExtra(USER_LATITUDE)){

                userLatitude = data.getDoubleExtra(USER_LATITUDE, 0);
                userLongitude = data.getDoubleExtra(USER_LONGITUDE, 0);
                if(userLatitude!=0 && userLongitude!=0) {
                    userLocationKnown = true;
                }
            }
            setUpMap();
        }
    };

}
