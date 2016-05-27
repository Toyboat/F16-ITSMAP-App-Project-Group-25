package com.example.victor.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.victor.finalproject.Datacontainers.Item;
import com.example.victor.finalproject.Helpers.ServerService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//todo: implement "Login" via SharedPreferences - ask them to create a username at first login, then save the username locally and use it in this activity to say hello
    public final int THUMBNAIL = 667;

    private Button lostButton;
    private Button foundButton;
    private Button searchButton;
    private Button requestButton;
    private Button uploadButton;
    private Button settingsButton;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.shared_prefs_id), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        if (!sharedpreferences.contains(ProjectConstants.SharedPrefs_ServerAddress))
        {
            editor.putString(ProjectConstants.SharedPrefs_ServerAddress,getString(R.string.server_address));
            editor.commit();
        }

        context = this;
        lostButton  = (Button) findViewById(R.id.lostButton);
        foundButton = (Button) findViewById(R.id.foundButton);
        searchButton = (Button) findViewById(R.id.searchButton);

        requestButton = (Button) findViewById(R.id.btnRequest);
        uploadButton = (Button) findViewById(R.id.btnUpload);
        settingsButton  = (Button) findViewById(R.id.btnSettings);

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

                ServerService.searchFor(context, new Item(10, "", Item.JSONLocationParse("{\"lat\":56.0, \"lon\":10.0,\"radius\":1000}"), 10, 10, new ArrayList<String>(), null));
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, THUMBNAIL);
                }else
                {
                    Toast.makeText(MainActivity.this, "Image request denied!", Toast.LENGTH_LONG).show();
                }}
                catch(Exception e)
                {
                    e.printStackTrace();
                }

            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSettingsIntent = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(toSettingsIntent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode)
        {
            case THUMBNAIL:
                if (resultCode == RESULT_OK)
                {
                    Bundle extras = data.getExtras();
                    Bitmap tn = (Bitmap) extras.get("data");
                    int width = tn.getWidth();
                    int height = tn.getHeight();
                    double newWidth = 100;
                    double newHeight = height * newWidth/width;

                    Bitmap newBitmap = Bitmap.createScaledBitmap(tn, (int)newWidth, (int)newHeight,false);

                    ServerService.storeItem(context, new Item(10, "Custom Thumbnail", Item.JSONLocationParse("{\"lat\":56.0, \"lon\":10.0,\"radius\":1000}"), 10, 10, new ArrayList<String>(), newBitmap));

                }
                break;
            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
