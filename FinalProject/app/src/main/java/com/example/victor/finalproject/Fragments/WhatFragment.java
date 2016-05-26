package com.example.victor.finalproject.Fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private WhatWhenWhereInterface fragmentInterface;
    private TextView what;

    public WhatFragment() {
        // Required empty public constructor
    }
    public static WhatFragment newInstance() {
        WhatFragment fragment = new WhatFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_what, container, false);
        what = (TextView) view.findViewById(R.id.whatfragmentButton);
        what.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentInterface != null) {
                    Toast toast = Toast.makeText(getContext(), "Clicked!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_what, container, false);
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

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    private void expandWhat(){

    }
}
