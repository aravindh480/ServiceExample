package com.example.serviceexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * Services by default run on the thread the service is created on.
 */
public class StartedService extends Service {
    /**
     * Tag to be used in log statements.
     */
    private static final String TAG = "MyService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        Log.i(TAG, "onStartCommand");
        // do some meaningful work
        final String messageFromActivity = intent.getStringExtra("message");
        Log.i(TAG, "Message: " + messageFromActivity);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }
}
