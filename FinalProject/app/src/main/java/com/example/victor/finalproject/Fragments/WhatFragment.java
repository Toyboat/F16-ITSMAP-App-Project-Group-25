package com.example.victor.finalproject.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.victor.finalproject.R;
import com.example.victor.finalproject.WhatWhenWhereInterface;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WhatWhenWhereInterface} interface
 * to handle interaction events.
 * Use the {@link WhatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WhatFragment extends Fragment {
    private static final String moduleName = "WhatFragment";

    private WhatWhenWhereInterface fragmentInterface;
    private TextView what;
    private View view;

    public WhatFragment() {
        // Required empty public constructor
        Log.d(moduleName,"Constructor();");
    }
    public static WhatFragment newInstance() {
        Log.d(moduleName,"newInstance();");
        WhatFragment fragment = new WhatFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(moduleName,"onCreate();");
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d(moduleName,"onCreateView();");
        view = inflater.inflate(R.layout.fragment_what, container, false);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        Log.d(moduleName,"onAttach();");
        super.onAttach(activity);
        try{
            fragmentInterface = (WhatWhenWhereInterface) activity;
        } catch (ClassCastException ex) {
            //Activity does not implement correct interface
            throw new ClassCastException(activity.toString() + " must implement WhatWhenWhereInterface");
        }
    }
    public void onClick() {
        Log.d(moduleName,"onClick();");
        Log.d("OnClickListener", "Clicked!");
        Toast toast = Toast.makeText(getContext(), "Clicked!", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void expand(String s){
        Log.d(moduleName,"expand();");
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(s == "lost"){
            view = inflater.inflate(R.layout.what_lost_opened, null);
        }else{
            view = inflater.inflate(R.layout.what_found_opened, null);
        }
        ViewGroup rootView = (ViewGroup) getView();
        rootView.removeAllViews();
        rootView.addView(view);
    }
    public void compress(String s){
        Log.d(moduleName,"compress();");
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.fragment_what, null);
        ViewGroup rootView = (ViewGroup) getView();
        rootView.removeAllViews();
        rootView.addView(view);
    }

    @Override
    public void onDetach() {

        Log.d(moduleName,"onDetach()");
        super.onDetach();
    }
    //todo: return data
    public String getDescription() {
        Log.d(moduleName,"getDescription();");

        return null;
    }
    //todo: return data
    public Bitmap getThumbnail() {
        Log.d(moduleName,"getThumbnail();");

        return null;
    }
}
