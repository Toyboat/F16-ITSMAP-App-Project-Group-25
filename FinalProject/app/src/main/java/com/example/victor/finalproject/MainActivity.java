package com.example.victor.finalproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.victor.finalproject.Datacontainers.Item;
import com.example.victor.finalproject.Helpers.ServerService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button lostButton;
    private Button foundButton;
    private Button searchButton;
    private Button requestButton;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        lostButton  = (Button) findViewById(R.id.lostButton);
        foundButton = (Button) findViewById(R.id.foundButton);
        searchButton = (Button) findViewById(R.id.searchButton);

        requestButton = (Button) findViewById(R.id.btnRequest);

        lostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LostActivity.class);
                startActivity(i);
            }
        });
        foundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FoundActivity.class);
                startActivity(i);
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SearchResultsActivity.class);
                startActivity(i);
            }
        });

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ServerService a = new ServerService();
                //public Item(int id, String description, Location location, int userid, int timestamp, List<String> tags, Bitmap thumbnail)

                ServerService.searchFor(context,new Item(10,"",Item.JSONLocationParse("{\"lat\":56.0, \"lon\":10.0,\"radius\":1000}"),10,10,new ArrayList<String>(), null));
            }
        });
    }

}
