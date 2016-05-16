package com.example.victor.finalproject.Helpers;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    public static void populateView(View view, Item item)
    {
        ImageView thumbnail;
        TextView txtDescription;
        TextView txtTags;
        TextView txtTime;

        thumbnail = (ImageView) view.findViewById(R.id.imgThumbnail);
        txtDescription = (TextView) view.findViewById(R.id.txtDescriptionFinal);
        txtTags = (TextView) view.findViewById(R.id.txtTags);
        txtTime = (TextView) view.findViewById(R.id.txtTime);

        thumbnail.setImageBitmap(item.thumbnail);
        txtDescription.setText(item.description);
        txtTags.setText(item.tags.toString());
        txtTime.setText(Integer.toString(item.timestamp));
    }
}
