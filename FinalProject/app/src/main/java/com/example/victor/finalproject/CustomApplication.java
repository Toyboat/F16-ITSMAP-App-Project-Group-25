package com.example.victor.finalproject;

import android.app.Application;
import android.util.Log;

import com.example.victor.finalproject.Datacontainers.LocationSingleton;
import com.example.victor.finalproject.Datacontainers.SearchResultsSingleton;
import com.example.victor.finalproject.Datacontainers.VolleyQueueInstance;

/**
 * Created by Jacob on 27/05/2016.
 */
public class CustomApplication extends Application {

    @Override
    public void onCreate()
    {
        super.onCreate();

        // Initialize the singletons so their instances
        // are bound to the application process.
        initSingletons();
    }

    private void initSingletons()
    {
        Log.println(Log.DEBUG,"CustomApplication","initSingletons()");
        SearchResultsSingleton.initInstance();
        VolleyQueueInstance.initInstance(getApplicationContext());
        LocationSingleton.initInstance();
    }

}
