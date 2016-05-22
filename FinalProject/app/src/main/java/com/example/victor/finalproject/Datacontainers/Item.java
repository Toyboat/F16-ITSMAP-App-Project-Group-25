package com.example.victor.finalproject.Datacontainers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacob on 16/05/2016.
 */
public class Item {
    //TODO: make parcelable or serializable

    public final int id;
    public final String description;
    public final Location location;
    public final int userid;
    public final int timestamp;
    public final Bitmap thumbnail;
    public final List<String> tags;

    public Item(int id, String description, Location location, int userid, int timestamp, List<String> tags, Bitmap thumbnail)
    {
        this.id = id;
        this.description = description;
        this.location = location;
        this.userid = userid;
        this.timestamp = timestamp;
        this.tags = tags;
        this.thumbnail = thumbnail;
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


    public static Item getDummy()
    {
        return new Item(0,"DummyDescription",new Location("Loco"),0,0,new ArrayList<String>(), null);
    }


    public static final String jsonID = "refid", jsonDescription = "description", jsonLocation = "location";
    public static final String jsonUserID = "userid", jsonTimeStamp = "timestamp", jsonThumbnail = "thumbnail";
    public static final String jsonTags = "tags", jsonLat="lat",jsonLon="lon",jsonRadius="radius",jsonTag="tag";

    public String toJSONString() {
        return toJSONString(this);
    }

    public static String JSONLocationGEN(Location loc)
    {
        return JSONLocationGEN(loc.getLatitude(), loc.getLongitude(), loc.getAccuracy());
    }

    public static String JSONLocationGEN(double lat, double lon, double radius)
    {
        String result = "{}";
        try{
        JSONObject job = new JSONObject();
            job.put(jsonLat,lat);
            job.put(jsonLon, lon);
            job.put(jsonRadius, radius);
            result = job.toString();

        //return "{\"lat\":"+Double.toString(lat)+",\"lon\":"+Double.toString(lon)+",\"radius\":"+Double.toString(radius)+"}";
        }catch(Exception e){}
        return result;
    }

    public static Location JSONLocationParse(String jsonLocationString)
    {
        Location loc = new Location("LEF");
        try {
            JSONObject job = new JSONObject(jsonLocationString);
            Double lat,lon,radius;

            lat = job.getDouble(jsonLat);
            lon = job.getDouble(jsonLon);
            radius = job.getDouble(jsonRadius);

            loc.setLatitude(lat);
            loc.setLongitude(lon);
            loc.setAccuracy(radius.floatValue());

        }catch(Exception e){}

        return loc;
    }



    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String BitmapToBase64(Bitmap bitmap)
    {
        String encoded = "";
        if (bitmap != null)
        {
            try{
                encoded = encodeToBase64(bitmap, Bitmap.CompressFormat.PNG,100);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        return encoded;
    }

    public static Bitmap Base64ToBitmap(String encoded)
    {
        Bitmap bitmap = null;

        if (encoded != null)
        {
            try{
                bitmap = decodeBase64(encoded);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        return bitmap;
    }

    public static String StringListToJson(List<String> strings)
    {
        String result = "[]";
        try {
            JSONArray arr = new JSONArray(strings);
            result = arr.toString();
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }

    public static List<String> JsonToStringList(String jsonListString)
    {
        List<String> strings = new ArrayList<String>();
        try {
            JSONArray arr = new JSONArray(jsonListString);
            for (int x = 0; x < arr.length(); x++)
            {
                strings.add(arr.get(x).toString());
            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }

        return strings;
    }

    public static String toJSONString(Item i)
    {
        String result = "{}";
        try {
            JSONObject jObj = new JSONObject();
            jObj.put(jsonID, i.id);
            jObj.put(jsonDescription,i.description);

            String location = JSONLocationGEN(i.location);
            jObj.put(jsonLocation,new JSONObject(location));

            jObj.put(jsonUserID, i.userid);
            jObj.put(jsonTimeStamp,i.timestamp);
            jObj.put(jsonThumbnail,BitmapToBase64(i.thumbnail));
            jObj.put(jsonTags,StringListToJson(i.tags));
            result = jObj.toString();
        }catch(Exception e)
        {}

        return result;
    }

    public static Item fromJSONString(String jsonString)
    {

        Item item = getDummy();
        try {
            JSONObject jObj = new JSONObject(jsonString);
            //public Item(int id, String description, Location location, int userid, int timestamp, List<String> tags, Bitmap thumbnail)
            int id = jObj.getInt(jsonID);
            String description = jObj.getString(jsonDescription);
            Location location = JSONLocationParse(jObj.getJSONObject(jsonLocation).toString());
            int userid = jObj.getInt(jsonUserID);
            int timestamp = jObj.getInt(jsonTimeStamp);
            List<String> tags = JsonToStringList(jObj.get(jsonTags).toString());
            Bitmap thumbnail = Base64ToBitmap(jObj.getString(jsonThumbnail));

            item = new Item(id,description,location,userid,timestamp,tags,thumbnail);

        }catch(Exception e)
        {
            e.printStackTrace();
        }


        return item;


    }
}
