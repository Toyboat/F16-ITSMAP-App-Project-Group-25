package com.example.victor.finalproject;

import android.app.FragmentManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.victor.finalproject.Fragments.WhatFragment;
import com.example.victor.finalproject.Fragments.WhenFragment;
import com.example.victor.finalproject.Fragments.WhereFragment;

public class LostActivity extends FragmentActivity implements WhatWhenWhereInterface{

    private WhatWhenWhereInterface fragmentInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);
        FragmentManager fm = getFragmentManager();
        //FragmentTransaction ft = fm.beginTransaction();
        Configuration config = getResources().getConfiguration();

    }
    public void expandWhat(){

    }
    public void onClick(View v){
        Log.d("LostActivity", "Clicked!");
    }

}
