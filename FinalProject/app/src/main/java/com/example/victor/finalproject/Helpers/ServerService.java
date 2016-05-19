package com.example.victor.finalproject.Helpers;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;

import com.example.victor.finalproject.Datacontainers.Item;

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

    public static void searchFor(Item i)
    {


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
        List dummyList = new ArrayList<Item>();
        for (int i= 0; i < 20; i++)
        {
            dummyList.add(Item.getDummy());
        }
        return dummyList;
    }

    private int storeItem(Item i){
        //TODO: store data on server
        return 0;
    }
}
