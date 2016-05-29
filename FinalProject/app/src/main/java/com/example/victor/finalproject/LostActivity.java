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
import android.widget.EditText;
import android.widget.ImageView;

import com.example.victor.finalproject.Datacontainers.Item;
import com.example.victor.finalproject.Fragments.WhatFragment;
import com.example.victor.finalproject.Fragments.WhenFragment;
import com.example.victor.finalproject.Fragments.WhereFragment;

public class LostActivity extends FragmentActivity implements WhatWhenWhereInterface{
    final static String SAVED_LOST_DESC = "savedLostDesc";
    private WhatWhenWhereInterface fragmentInterface;
    private WhatFragment whatf;
    private WhenFragment whenf;
    private WhereFragment wheref;
    private Button search;
    private Button cancel;
    private String inputLost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);
        inputLost = getString(R.string.description);
        FragmentManager fm = getFragmentManager();
        if(savedInstanceState != null){

        }

        //FragmentTransaction ft = fm.beginTransaction();
        Configuration config = getResources().getConfiguration();
        search = (Button) findViewById(R.id.searchButton);
        cancel = (Button) findViewById(R.id.cancelButton);
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
        whatf = (WhatFragment) getSupportFragmentManager().findFragmentById(R.id.whatFragment);
        whenf = (WhenFragment) getSupportFragmentManager().findFragmentById(R.id.whenFragment);
        wheref = (WhereFragment) getSupportFragmentManager().findFragmentById(R.id.whereFragment);
    }

    public void expand(int id){

        String s = getString(R.string.lost_activity);
        switch(id){
            case 1://expand what
                this.whatf.expand(s);
                //this.whatf.lostDescriptionEditor.setText(inputLost);

                this.whenf.compress(s);
                this.wheref.compress(s);
            break;
            case 2: //expand when
                this.whatf.compress(s);
                this.whenf.expand(s);
                this.wheref.compress(s);

            break;
            case 3: //expand where
                this.whatf.compress(s);
                this.whenf.compress(s);
                this.wheref.expand(s);
            break;
        }
    }
    public void onClick(View v){

        switch (v.getId()){
            case R.id.whatfragmentButton:
                expand(1);
                //inputLost = this.whatf.lostDescriptionEditor.getText().toString();
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
        String description = this.whatf.getDescription();
        Bitmap thumbnail = this.whatf.getThumbnail();
        Location location = this.wheref.getUserLocation();
        this.whenf.requestData();
      // Item item = new Item(int id, description, location, int userid, int timestamp, List<String> tags, thumbnail);
    }

    /** /
    public void onSaveInstanceState(Bundle savedInstanceState){
        //Save the fragment's instance
        savedInstanceState.putString(SAVED_LOST_DESC, inputLost);
    }
     /**/
}
