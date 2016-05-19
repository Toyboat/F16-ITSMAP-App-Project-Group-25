package com.example.victor.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.victor.finalproject.Datacontainers.Item;
import com.example.victor.finalproject.Helpers.SearchResultsListViewAdapter;
import com.example.victor.finalproject.Helpers.ServerService;

import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {


    public static final String itemsString = "SRA_ITEMSLIST", adapterString = "SRA_LVADAPTER";
    private ListView resultsList;
    private SearchResultsListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        resultsList = (ListView) findViewById(R.id.resultsList);



        //TODO: receive service completed notification


        //TODO: get item list from service using interface and present in listview.
        updateList();

        resultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelected(position);
                adapter.notifyDataSetChanged();
            }
        });

     }

    public void updateList()
    {
        adapter = new SearchResultsListViewAdapter(resultsList, this, ServerService.getResults() );
        resultsList.setAdapter(adapter);
    }
}
