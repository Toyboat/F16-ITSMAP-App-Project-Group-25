package com.example.victor.finalproject;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;

public class ServerService extends Service {
    //todo: implement server and connect
    public ServerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private List<Item> getResults(Item i){
        //TODO: getDataFromServer

        //======================================Dummylist - remove when we have a server:
        List dummyList = new ArrayList<Item>();
        Location dummyLoco = new Location("DummyLocation");
        List dummyTags = new ArrayList<String>();
        dummyTags.add("Dummytag1");
        dummyTags.add("Dummytag2");
        Item i1 = new Item(1, "DummyItem1", dummyLoco, 1, 1200, dummyTags);
        Item i2 = new Item(2, "DummyItem2" ,  dummyLoco, 2, 1200, dummyTags);
        dummyList.add(i1);
        dummyList.add(i2);

        //=====================================end of dummy list
        return dummyList;
    }

    private int storeItem(Item i){
        //TODO: store data on server
        return 0;
    }
}
