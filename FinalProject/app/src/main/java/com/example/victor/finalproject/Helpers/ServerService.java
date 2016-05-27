package com.example.victor.finalproject.Helpers;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.victor.finalproject.Datacontainers.Item;
import com.example.victor.finalproject.Datacontainers.SearchResultsSingleton;
import com.example.victor.finalproject.ProjectConstants;
import com.example.victor.finalproject.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.shared_prefs_id), Context.MODE_PRIVATE);
        String address = sp.getString(ProjectConstants.SharedPrefs_ServerAddress,context.getString(R.string.server_address));

               // String address = context.getResources().getString(R.string.server_address);
        String subdir = context.getString(R.string.server_subdir);
        String scriptname = context.getString(R.string.server_search_script);
        String url = "http://" + address+ "/" + subdir + "/" + scriptname + "?jsonItem=";

        url += i.toJSONString();
        Log.println(Log.DEBUG,"StringRequest","URL: "+url);
        Toast.makeText(context,url,Toast.LENGTH_LONG).show();

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
                    ServerService.setResults(items);
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


    //public static List<Item> items;

    public static void setResults(List<Item> items)
    {
        SearchResultsSingleton.getInstance().setSearchResults(items);
    }

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
        /*
        if (items == null) {
            List dummyList = new ArrayList<Item>();
            for (int i = 0; i < 20; i++) {
                dummyList.add(Item.getDummy());
            }
            items = dummyList;
        }


        return items;
        */
        return SearchResultsSingleton.getInstance().getSearchResults();
    }

    public static void clearItems()
    {
        SearchResultsSingleton.getInstance().cleanup();
    }


    public static int storeItem(Context context,Item i) {
        //TODO: store data on server
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.shared_prefs_id), Context.MODE_PRIVATE);
        String address = sp.getString(ProjectConstants.SharedPrefs_ServerAddress, context.getString(R.string.server_address));

        String subdir = context.getResources().getString(R.string.server_subdir);
        String scriptname = context.getResources().getString(R.string.server_upload_script);
        String url = "http://" + address + "/" + subdir + "/" + scriptname;
        //+ "?jsonItem=";

        //url += i.toJSONString();

        Log.println(Log.DEBUG, "StringRequest", "URL: " + url);
        Toast.makeText(context, url, Toast.LENGTH_LONG).show();
        final String itemJson = i.toJSONString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            public int refid = -1;

            @Override
            public void onResponse(String response) {
                Log.println(Log.DEBUG, "StringRequest", "GOT: " + response);
                try {
                    JSONObject result = new JSONObject(response);
                    if (result.getBoolean("result")) {
                        refid = result.getInt("refid");
                        Log.println(Log.DEBUG, "StringRequest", "Upload success: " + response);
                    } else {
                        Log.println(Log.DEBUG, "StringRequest", "Didn't upload: " + response);

                    }

                    //Item item = Item.fromJSONString(response);
                    //ServerService.items = items;
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.println(Log.DEBUG, "StringRequest", "Error: " + response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.println(Log.DEBUG, "StringRequest", "Error!");
                error.printStackTrace();
            }
        }) {

            @Override
            protected Map<String, String> getParams () throws AuthFailureError
            {
            Map<String, String> params = new HashMap<String, String>();
            params.put("jsonItem", itemJson);
            return params;
        }

        @Override
        public Map<String, String> getHeaders ()throws AuthFailureError {
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("Content-Type", "application/x-www-form-urlencoded");
            return headers;
        }
    };
        requestQueue.add(stringRequest);

        return 0;
    }
}
