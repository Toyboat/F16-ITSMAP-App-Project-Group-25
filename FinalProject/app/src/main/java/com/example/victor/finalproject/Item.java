package com.example.victor.finalproject;

import android.graphics.Bitmap;
import android.location.Location;

import java.util.List;

/**
 * Created by Jacob on 16/05/2016.
 */
public class Item {
    public final int id;
    public final String description;

    public final Location location;
    public final int userid;
    public final int timestamp;

    private Bitmap thumbnail;
    private List<Bitmap> photos;

    public final List<String> tags;

    public Item(int id, String description, Location location, int userid, int timestamp, List<String> tags)
    {
        this.id = id;
        this.description = description;
        this.location = location;
        this.userid = userid;
        this.timestamp = timestamp;
        this.tags = tags;
    }
    public int getId(){
        return this.id;
    }
    public String getDescription(){
        return this.description;
    }
    public Location getLocation(){
        return this.location;
    }
    public int getUserId(){
        return this.userid;
    }
    public int getTimeStamp(){
        return this.timestamp;
    }
    public List<String> getTags(){
        return this.tags;
    }

}
