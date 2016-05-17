package com.example.victor.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.victor.finalproject.Datacontainers.Item;
import com.example.victor.finalproject.Helpers.SearchResultsListViewAdapter;
import com.example.victor.finalproject.Helpers.ServerService;

import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {


    public static final String itemsString = "SRA_ITEMSLIST", adapterString = "SRA_LVADAPTER";
    private ListView resultsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        resultsList = (ListView) findViewById(R.id.resultsList);



        //TODO: receive service completed notification


        //TODO: get item list from service using interface and present in listview.
        SearchResultsListViewAdapter adapter = new SearchResultsListViewAdapter(resultsList, this, ServerService.getResults() );
        resultsList.setAdapter(adapter);
    }
}
