package com.realityexpander.servicesamplerfromvogella;


import java.util.ArrayList;
import java.util.List;

//import android.R;
//import android.app.Activity;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;


public class MainActivity extends ListActivity {
    private LocalWordService s;

    public class Banana extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
    private final BroadcastReceiver theBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                if(extras.containsKey("value")){
                    System.out.println("Value is:"+extras.get("value"));
                    //Toast.makeText(MainActivity.this, "Value is:"+extras.get("value"), Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,
                R.layout.simple_list_item_1, R.id.text1,
                wordList);
        setListAdapter(adapter);

        //getApplicationContext().startService(new Intent(this, MyStartServiceReceiver.class));
        //getApplicationContext().startService(new Intent(this, MyScheduleReceiver.class));
        startService(new Intent(MainActivity.this, MyScheduleReceiver.class));  // Cant be restarted unless @ boot time?
        startService(new Intent(MainActivity.this, LocalWordService.class));

    }


    @Override
    protected void onResume() {
        super.onResume();
        bindService(new Intent(this, LocalWordService.class), mConnection,
                Context.BIND_AUTO_CREATE);
        Toast.makeText(MainActivity.this, "Bound", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(mConnection);
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder binder) {
            s = ((LocalWordService.MyBinder) binder).getService();
            Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT)
                    .show();
        }

        public void onServiceDisconnected(ComponentName className) {
            s = null;
            Toast.makeText(MainActivity.this, "Disconnected", Toast.LENGTH_SHORT)
                    .show();
        }
    };
    private ArrayAdapter<String> adapter;
    private List<String> wordList;


    public void showServiceData(View view) {
        if (s != null) {

            Toast.makeText(this, "Number of elements" + s.getWordList().size(),
                    Toast.LENGTH_SHORT).show();
            wordList.clear();
            wordList.addAll(s.getWordList());
            adapter.notifyDataSetChanged();
        }
    }

}

