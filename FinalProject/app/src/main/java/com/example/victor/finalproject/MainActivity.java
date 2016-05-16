package com.example.victor.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button lostButton = (Button) findViewById(R.id.foundButton);
    private Button foundButton = (Button) findViewById(R.id.lostButton);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }

}
