package com.example.victor.finalproject.Helpers;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.victor.finalproject.Datacontainers.Item;
import com.example.victor.finalproject.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ServerService extends Service {
    //todo: implement server and connect
    public ServerService() {
    }
    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        ServerService getService(){
            return ServerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    //public static void searchFor(Item i)
    public static void searchFor(Context context,Item i)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String address = context.getResources().getString(R.string.server_address);
        String scriptname = context.getResources().getString(R.string.server_search_script);
        String url = address + scriptname + "?jsonItem=";

        url += i.toJSONString();
        Log.println(Log.DEBUG,"StringRequest","URL: "+url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.println(Log.DEBUG,"StringRequest","GOT: "+response);
                try {
                    JSONArray arr = new JSONArray(response);
                    List<Item> items = new ArrayList<Item>();
                    for (int x = 0; x < arr.length(); x++)
                    {
                        items.add(Item.fromJSONString(arr.get(x).toString()));
                    }
                    //Item item = Item.fromJSONString(response);
                    ServerService.items = items;
                }catch(Exception e)
                {
                    e.printStackTrace();
                    Log.println(Log.DEBUG,"StringRequest","Error: " +  response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.println(Log.DEBUG,"StringRequest","Error!");
                error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);

    }


    public static List<Item> items;
    public static List<Item> getResults(){
        //TODO: getDataFromServer
/*
        //======================================Dummylist - remove when we have a server:
        List dummyList = new ArrayList<Item>();
        Location dummyLoco = new Location("DummyLocation");
        List dummyTags = new ArrayList<String>();
        dummyTags.add("Dummytag1");
        dummyTags.add("Dummytag2");
        Item i1 = new Item(1, "DummyItem1", dummyLoco, 1, 1200, dummyTags, null);
        Item i2 = new Item(2, "DummyItem2" ,  dummyLoco, 2, 1200, dummyTags, null);
        dummyList.add(i1);
        dummyList.add(i2);

        //=====================================end of dummy list
        */
        if (items == null) {
            List dummyList = new ArrayList<Item>();
            for (int i = 0; i < 20; i++) {
                dummyList.add(Item.getDummy());
            }
            items = dummyList;
        }
        return items;
    }

    private int storeItem(Item i){
        //TODO: store data on server
        return 0;
    }
}
