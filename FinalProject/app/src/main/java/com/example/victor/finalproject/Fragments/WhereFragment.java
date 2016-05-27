package com.example.victor.finalproject.Fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.victor.finalproject.R;
import com.example.victor.finalproject.WhatWhenWhereInterface;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WhereFragment.WhereFragmentListener} interface
 * to handle interaction events.
 * Use the {@link WhereFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WhereFragment extends Fragment {
    private WhatWhenWhereInterface fragmentInterface;
    private View view;

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
        try{
            fragmentInterface = (WhatWhenWhereInterface) activity;
        } catch (ClassCastException ex) {
            //Activity does not implement correct interface
            throw new ClassCastException(activity.toString() + " must implement WhatWhenWhereInterface");
        }
    }
    public void expand(){
        Log.d("expand called","from WhatFragment");
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.where_lost_opened, null);
        ViewGroup rootView = (ViewGroup) getView();
        rootView.removeAllViews();
        rootView.addView(view);
    }
    public void compress(){
        Log.d("expand called","from WhatFragment");
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


}
