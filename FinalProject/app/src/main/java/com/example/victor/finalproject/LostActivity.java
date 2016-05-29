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
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.victor.finalproject.Datacontainers.Item;
import com.example.victor.finalproject.Datacontainers.LocationSingleton;
import com.example.victor.finalproject.Helpers.ServerService;

import java.util.ArrayList;
import java.util.List;

public class LostActivity extends Activity {
    final static String SAVED_DESCRIPTION = "savedDescription";
    private Button search;
    private Button cancel;
    private Button locater;
    private LocationManager locationManager;
    private static final long MIN_TIME_BETWEEN_LOCATION_UPDATES = 5 * 1000;    // milisecs
    private static final float MIN_DISTANCE_MOVED_BETWEEN_LOCATION_UPDATES = 1;  // meter
    public final int THUMBNAIL = 667;
    private boolean isTracking = false;
    private ImageView imageButton;
    private EditText description;
    private EditText latView;
    private EditText lonView;
    private EditText radView;
    private Location userLocation;
    private Bitmap thumbnail;
    private List<String> tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userLocation = null;
        List<String> tags = new ArrayList<String>();
        tags.add("sort");
        tags.add("sofa");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);

        if(savedInstanceState != null){
            description.setText(savedInstanceState.getString(SAVED_DESCRIPTION));
        }

        search = (Button) findViewById(R.id.lostSearchButton);
        cancel = (Button) findViewById(R.id.lostCancelButton);
        locater = (Button) findViewById(R.id.locater);
        latView = (EditText) findViewById(R.id.txtLat);
        lonView = (EditText) findViewById(R.id.txtLon);
        radView = (EditText) findViewById(R.id.txtRad);
        description = (EditText) findViewById(R.id.editLostDescriptionText);
        imageButton = (ImageView) findViewById(R.id.imageButton);
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
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(cameraIntent, THUMBNAIL);
                    }else {
                        Toast.makeText(LostActivity.this, "Image request denied!", Toast.LENGTH_LONG).show();
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
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
            showLocation(userLocation);
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode)
        {
            case THUMBNAIL:
                if (resultCode == RESULT_OK)
                {
                    Bundle extras = data.getExtras();
                    Bitmap tn = (Bitmap) extras.get("data");
                    int width = tn.getWidth();
                    int height = tn.getHeight();
                    double newWidth = 100;
                    double newHeight = height * newWidth/width;

                    Bitmap newBitmap = Bitmap.createScaledBitmap(tn, (int)newWidth, (int)newHeight,false);

                    long time = System.currentTimeMillis()/1000;

                    newBitmap = thumbnail;
                    imageButton.setImageBitmap(newBitmap);
                }
                break;
            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showLocation(Location l) {
        lonView.setText(Double.toString(l.getLongitude()));
        latView.setText(Double.toString(l.getLatitude()));
        radView.setText(Float.toString(l.getAccuracy()));

    }

    private void senddata() {
        //inserts dummy data: for :id, userId and timestamp
        int id = 01;
        int userId = 01;
        Long timestamp = System.currentTimeMillis()/1000;
        Item item = new Item(id, description.getText().toString(), userLocation, userId, timestamp, tags, thumbnail);
        ServerService.storeItem(this,item);
    }
    public void onSaveInstanceState(Bundle savedInstanceState){
        //Save the fragment's instance
        savedInstanceState.putString(SAVED_DESCRIPTION, description.getText().toString());
    }
    @Override
    protected void onDestroy() {
        UnregisterBroadcastReceivers();
        super.onDestroy();
    }

}
