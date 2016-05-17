package com.example.victor.finalproject.Helpers;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.victor.finalproject.Datacontainers.Item;
import com.example.victor.finalproject.R;

/**
 * Created by Jacob on 16/05/2016.
 */
public class SRListItemSelected {
    /*
    private ImageView thumbnail;
    private TextView txtDescription;
    private TextView txtTags;
    private TextView txtTime;
    */

    private SRListItemSelected(){}


    public static void populateView(int position, Context context, View view, Item item)
    {

        ImageView thumbnail;
        TextView txtDescription;
        TextView txtTags;
        TextView txtTime;
        Button btnClick;

        thumbnail = (ImageView) view.findViewById(R.id.imgThumbnail);
        txtDescription = (TextView) view.findViewById(R.id.txtDescriptionFinal);
        txtTags = (TextView) view.findViewById(R.id.txtTags);
        txtTime = (TextView) view.findViewById(R.id.txtTime);
        btnClick = (Button) view.findViewById(R.id.btnClick);

        if (item.thumbnail != null) {
            thumbnail.setImageBitmap(item.thumbnail);
        }
        txtDescription.setText(item.description);
        txtTags.setText(item.tags.toString());
        txtTime.setText(Integer.toString(item.timestamp));

        final Item it = item;
        final Context cont = context;
        final int pos = position;

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.println(Log.DEBUG,"Item Button","Button Clicked! " + it.id);
                Toast.makeText(cont, "Button clicked " + pos, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
