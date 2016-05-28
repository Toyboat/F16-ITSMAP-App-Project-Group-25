package com.example.victor.finalproject;

import android.app.FragmentManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.victor.finalproject.Datacontainers.Item;
import com.example.victor.finalproject.Fragments.WhatFragment;
import com.example.victor.finalproject.Fragments.WhenFragment;
import com.example.victor.finalproject.Fragments.WhereFragment;

public class LostActivity extends FragmentActivity implements WhatWhenWhereInterface{
    private WhatWhenWhereInterface fragmentInterface;
    private WhatFragment whatf;
    private WhenFragment whenf;
    private WhereFragment wheref;
    private Button search;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);
        FragmentManager fm = getFragmentManager();
        //FragmentTransaction ft = fm.beginTransaction();
        Configuration config = getResources().getConfiguration();
        search = (Button) findViewById(R.id.searchButton);
        cancel = (Button) findViewById(R.id.cancelButton);
        whatf = (WhatFragment) getSupportFragmentManager().findFragmentById(R.id.whatFragment);
        whenf = (WhenFragment) getSupportFragmentManager().findFragmentById(R.id.whenFragment);
        wheref = (WhereFragment) getSupportFragmentManager().findFragmentById(R.id.whereFragment);
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
    }

    public void expand(int id){
        String s = getString(R.string.lost_activity);
        switch(id){
            case 1://expand what
                whatf.expand(s);
                whenf.compress(s);
                wheref.compress(s);
            break;
            case 2: //expand when
                whatf.compress(s);
                whenf.expand(s);
                wheref.compress(s);

            break;
            case 3: //expand where
                whatf.compress(s);
                whenf.compress(s);
                wheref.expand(s);
            break;
        }
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.whatfragmentButton:
                expand(1);
            break;
            case R.id.whenfragmentButton:
                expand(2);
            break;
            case R.id.wherefragmentButton:
               expand(3);
            break;
        }
    }

    private void senddata() {
        Log.d("Sender data...","not!");
        String description = whatf.getDescription();
        Bitmap thumbnail = whatf.getThumbnail();
        Location location = wheref.getUserLocation();
        whenf.requestData();
      // Item item = new Item(int id, description, location, int userid, int timestamp, List<String> tags, thumbnail);
    }
}
