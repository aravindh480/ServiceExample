package com.example.serviceexample;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class BindableService extends Service {

    private final IBinder mBinder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    class LocalBinder extends Binder {
        BindableService getService() {
            return BindableService.this;
        }
    }

    public int findFactorial(final int number) {
        int fact = 1;
        for (int i = 1; i <= number; i++) {
            fact *= i;
        }

        return fact;
    }
}
