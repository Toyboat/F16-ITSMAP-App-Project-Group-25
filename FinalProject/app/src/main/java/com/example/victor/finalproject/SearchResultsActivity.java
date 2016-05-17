package com.example.victor.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.victor.finalproject.Datacontainers.Item;
import com.example.victor.finalproject.Helpers.SearchResultsListViewAdapter;

import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {


    public static final String itemsString = "SRA_ITEMSLIST", adapterString = "SRA_LVADAPTER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        if (savedInstanceState == null)
        {


        }else
        {

        }
        //TODO: send intent to service with search criteria
        //TODO: receive service completed notification
        //TODO: get item list from service using interface and present in listview.
    }
}
