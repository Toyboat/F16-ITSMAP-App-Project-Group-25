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
import android.widget.EditText;
import android.widget.ImageView;
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
    static final String SAVED_DESCRIPTION = "savedDescription";
    static final String SAVED_BITMAP = "savedBitmap";
    public String description;
    private Bitmap bitmap;
    private EditText lostDescriptionEditor;
    private EditText foundDescriptionEditor;
    private ImageView imageView;

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
       //todo: bitmap = (Bitmap)
        if(savedInstanceState != null){
            description = savedInstanceState.getString("savedDescription");
            bitmap = savedInstanceState.getParcelable("savedBitmap");
        }
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
        lostDescriptionEditor = (EditText) getActivity().findViewById(R.id.editLostDescriptionText);
        foundDescriptionEditor =  (EditText) getActivity().findViewById(R.id.ediFoundDescriptionText);

        Log.d(moduleName,"expand();");
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(s == "lost"){
            lostDescriptionEditor.setText(description.toString());
            view = inflater.inflate(R.layout.what_lost_opened, null);

        }else{
            //foundDescriptionEditor.setText(description.toString());
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
        lostDescriptionEditor = null;
        foundDescriptionEditor = null;
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString(SAVED_DESCRIPTION, lostDescriptionEditor.getText().toString());
        savedInstanceState.putString(SAVED_DESCRIPTION, foundDescriptionEditor.getText().toString());
        savedInstanceState.putParcelable(SAVED_BITMAP, bitmap);
    }

    public void textSetter(String s, String input) {

        if(s == "lost"){
            lostDescriptionEditor.setText(input);
        }else{
            foundDescriptionEditor.setText(input);
        }
    }
    public String textGetter(String s){
        if(s == "lost"){
            return lostDescriptionEditor.getText().toString();
        }else{
            return foundDescriptionEditor.getText().toString();
        }
    }
}
