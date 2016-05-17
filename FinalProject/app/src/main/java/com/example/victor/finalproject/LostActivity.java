package com.example.victor.finalproject;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.victor.finalproject.Fragments.WhatFragment;
import com.example.victor.finalproject.Fragments.WhenFragment;
import com.example.victor.finalproject.Fragments.WhereFragment;

public class LostActivity extends FragmentActivity
        implements WhatFragment.WhatFragmentListener, WhenFragment.WhenFragmentListener, WhereFragment.WhereFragmentListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);
    }

    @Override
    public void onWhatFragmentInteraction(Uri uri) {

    }

    @Override
    public void onWhenFragmentInteraction(Uri uri) {

    }

    @Override
    public void onWhereFragmentInteraction(Uri uri) {

    }
}
