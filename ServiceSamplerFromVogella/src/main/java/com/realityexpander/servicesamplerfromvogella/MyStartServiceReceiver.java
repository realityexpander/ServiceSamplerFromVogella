package com.realityexpander.servicesamplerfromvogella;

/**
 * Created by realityexpander on 9/8/13.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class MyStartServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, LocalWordService.class);
        context.startService(service);



    }
}