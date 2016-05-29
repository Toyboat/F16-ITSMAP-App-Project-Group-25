package com.example.victor.finalproject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.victor.finalproject.Datacontainers.Item;
import com.example.victor.finalproject.Datacontainers.LocationSingleton;

public class LostActivity extends Activity {
    final static String CAPS_STRING = "refernaename";;
    private Button search;
    private Button cancel;
    private Button locater;
    private LocationManager locationManager;
    private static final long MIN_TIME_BETWEEN_LOCATION_UPDATES = 5 * 1000;    // milisecs
    private static final float MIN_DISTANCE_MOVED_BETWEEN_LOCATION_UPDATES = 1;  // meter
    private boolean isTracking = false;
    private EditText description;
    private EditText latView;
    private EditText lonView;
    private EditText radView;
    private Location userLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userLocation = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);

        if(savedInstanceState != null){

        }

        search = (Button) findViewById(R.id.lostSearchButton);
        cancel = (Button) findViewById(R.id.lostCancelButton);
        locater = (Button) findViewById(R.id.locater);
        latView = (EditText) findViewById(R.id.txtLat);
        lonView = (EditText) findViewById(R.id.txtLon);
        radView = (EditText) findViewById(R.id.txtRad);
        search.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                senddata();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        locater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTracking();
            }
        });
        SetupBroadcastReceivers();
    }

    private boolean startTracking() {
        try {
            if (locationManager == null) {
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            }

            long minTime = MIN_TIME_BETWEEN_LOCATION_UPDATES;
            float minDistance = MIN_DISTANCE_MOVED_BETWEEN_LOCATION_UPDATES;
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setPowerRequirement(Criteria.POWER_MEDIUM);

            if (locationManager != null) {
                try {
                    locationManager.requestLocationUpdates(minTime, minDistance, criteria, locationListener, null);
                } catch (SecurityException ex) {
                    //TODO: user have disabled location permission - need to validate this permission for newer versions
                }
            } else {
                return false;
            }
            isTracking = true;
            return true;
        } catch (Exception ex) {
            //things can go wrong
            Log.e("TRACKER", "Error during start", ex);
            return false;
        }
    }
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            LocationSingleton.getInstance().setLocation(location);

            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(new Intent(ProjectConstants.BroadcastLocationUpdateAction));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    private boolean stopTracking(){
        try {
            try{
                locationManager.removeUpdates(locationListener);
                isTracking = false;
            } catch (SecurityException ex) {
                //TODO: user have disabled location permission - need to validate this permission for newer versions
            }

            return true;
        } catch (Exception ex) {
            //things can go wrong here as well (listener is null)
            Log.e("TRACKER", "Error during stop", ex);
            return false;
        }
    }
    private void SetupBroadcastReceivers()
    {
        LocalBroadcastManager lbcm = LocalBroadcastManager.getInstance(this);
        lbcm.registerReceiver(locationUpdateReceiver, new IntentFilter(ProjectConstants.BroadcastLocationUpdateAction));
    }

    private void UnregisterBroadcastReceivers()
    {
        LocalBroadcastManager lbcm = LocalBroadcastManager.getInstance(this);
        lbcm.unregisterReceiver(locationUpdateReceiver);

    }
    private BroadcastReceiver locationUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"locationUpdateReceiver got "+ Item.JSONLocationGEN(LocationSingleton.getInstance().getLocation()),Toast.LENGTH_SHORT).show();
            stopTracking();
            userLocation = LocationSingleton.getInstance().getLocation();
            setLocation(userLocation);
        }
    };

    private void setLocation(Location l) {
        lonView.setText(Double.toString(l.getLongitude()));
        latView.setText(Double.toString(l.getLatitude()));
        radView.setText(Float.toString(l.getAccuracy()));

    }

    private void senddata() {
        Log.d("Sender data...","not!");
        String description = null;
        Bitmap thumbnail = null;
        Location location = null;
      // Item item = new Item(int id, description, location, int userid, int timestamp, List<String> tags, thumbnail);
    }
    public void onSaveInstanceState(Bundle savedInstanceState){
        //Save the fragment's instance
        //savedInstanceState.putString(CAPS_STRING, data);
    }
    @Override
    protected void onDestroy() {
        UnregisterBroadcastReceivers();
        super.onDestroy();
    }

}
