package com.bvmiste.vision16;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.PushService;
import com.parse.SaveCallback;

/**
 * Created by arvind on 15/1/16.
 */
public class Iniatilzation extends Application {
    private static Iniatilzation mInstance;
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);
        // Parse.enableLocalDatastore(this); // if you need this, put before init
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "PARSE KEY", "PARSE KEY");
        ParseObject id = ParseInstallation.getCurrentInstallation();

        ParseInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                ParsePush.subscribeInBackground("General");

                Log.e("com.parse.push", "done", e);
            }
        });
    }

    public static synchronized Iniatilzation getInstance() {
        return mInstance;
    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        

}
