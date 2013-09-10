package com.realityexpander.servicesamplerfromvogella;

/**
 * Created by realityexpander on 9/8/13.
 */

import android.app.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

public class LocalWordService extends Service {
    private final IBinder mBinder = new MyBinder();
    private ArrayList<String> list = new ArrayList<String>();

    private static int incrementor = 0;
    private static long lastCheckMilli = System.currentTimeMillis();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


//        Random random = new Random();
//        if (random.nextBoolean()) {
//            list.add("Linux");
//        }
//        if (random.nextBoolean()) {
//            list.add("Android");
//        }
//        if (random.nextBoolean()) {
//            list.add("iPhone");
//        }
//        if (random.nextBoolean()) {
//            list.add("Windows7");
//        }

        incrementor++;
        list.add(String.valueOf(incrementor) +"-->"+ String.valueOf( System.currentTimeMillis() - lastCheckMilli) );
        lastCheckMilli = System.currentTimeMillis();

        if (list.size() >= 20) {
            list.remove(0);
        }

        Intent intent2 = new Intent();
        intent2.setAction("MyBroadcast");
        intent2.putExtra("value", 1000);
        //sendBroadcast(intent);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent2);

        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return mBinder;
    }

    public class MyBinder extends Binder {
        LocalWordService getService() {
            return LocalWordService.this;
        }
    }

    public List<String> getWordList() {
        return list;
    }

}
