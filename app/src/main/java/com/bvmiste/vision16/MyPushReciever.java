package com.bvmiste.vision16;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;


import com.parse.ParsePushBroadcastReceiver;

/**
 * Created by arvind on 20/1/16.
 */
public class MyPushReciever extends ParsePushBroadcastReceiver {


    protected Class<? extends Activity> getActivity(Context context, Intent intent) {

         return NotificationFeed.class; // the activity that shows up
    }

}
