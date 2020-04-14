package com.example.serviceexample;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyIntentService extends IntentService {
    /**
     * Tag to be used in log statements.
     */
    private static final String TAG = "MyIntentService";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable final Intent intent) {
        // do some meaningful work here.
        Log.i(TAG, "onHandleIntent");
        final String messageFromActivity = intent.getStringExtra("message");
        Log.i(TAG, "Message: " + messageFromActivity);
    }
}
