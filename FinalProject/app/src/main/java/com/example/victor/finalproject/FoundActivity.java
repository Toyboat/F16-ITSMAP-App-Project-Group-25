package com.example.victor.finalproject;

import android.app.FragmentManager;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.victor.finalproject.Fragments.WhatFragment;
import com.example.victor.finalproject.Fragments.WhenFragment;
import com.example.victor.finalproject.Fragments.WhereFragment;

public class FoundActivity extends AppCompatActivity  implements WhatWhenWhereInterface{
        private WhatWhenWhereInterface fragmentInterface;
        private WhatFragment whatf;
        private WhenFragment whenf;
        private WhereFragment wheref;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_found);
            FragmentManager fm = getFragmentManager();
            //FragmentTransaction ft = fm.beginTransaction();
            Configuration config = getResources().getConfiguration();

        }
        public void expand(int id){
            String s = getString(R.string.found_activity);
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
        whatf = (WhatFragment) getSupportFragmentManager().findFragmentById(R.id.whatFragment);
        whenf = (WhenFragment) getSupportFragmentManager().findFragmentById(R.id.whenFragment);
        wheref = (WhereFragment) getSupportFragmentManager().findFragmentById(R.id.whereFragment);

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
}