package com.example.serviceexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private Button mStartServiceButton, mStopServiceButton;

    private Button mStartIntentServiceButton, mStopIntentServiceButton;

    private Button mBindServiceButton, mUnbindServiceButton;

    private Button mFindFactorialButton;

    private EditText mFactorialInputNumberEditText;

    private BindableService mBindableService;

    private boolean isServiceBound = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStartServiceButton = findViewById(R.id.start_service_button);
        mStopServiceButton = findViewById(R.id.stop_service_button);

        mStartIntentServiceButton = findViewById(R.id.start_intent_service_button);
        mStopIntentServiceButton = findViewById(R.id.stop_intent_service_button);

        mBindServiceButton = findViewById(R.id.bind_service_button);
        mUnbindServiceButton = findViewById(R.id.unbind_service_button);

        mFactorialInputNumberEditText = findViewById(R.id.number_edit_text);
        mFindFactorialButton = findViewById(R.id.find_factorial_button);

        setOnClickListeners();
    }

    private void setOnClickListeners() {
        mStartServiceButton.setOnClickListener(this);
        mStopServiceButton.setOnClickListener(this);
        mStartIntentServiceButton.setOnClickListener(this);
        mStopIntentServiceButton.setOnClickListener(this);
        mBindServiceButton.setOnClickListener(this);
        mUnbindServiceButton.setOnClickListener(this);
        mFindFactorialButton.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.start_service_button:
                final Intent startServiceIntent = new Intent();
                //TODO: Investigate whether we should use activity context or Application context.
                startServiceIntent.setClass(getApplicationContext(), StartedService.class);
                // pass some data to the service via the intent object.
                startServiceIntent.putExtra("message", "Do some meaningful work");
                startService(startServiceIntent);
                break;
            case R.id.stop_service_button:
                final Intent stopServiceIntent = new Intent();
                stopServiceIntent.setClass(getApplicationContext(), StartedService.class);
                stopService(stopServiceIntent);
                break;
            case R.id.start_intent_service_button:
                final Intent startIntentServiceIntent = new Intent();
                //TODO: Investigate whether we should use activity context or Application context.
                startIntentServiceIntent.setClass(getApplicationContext(), MyIntentService.class);
                // pass some data to the service via the intent object.
                startIntentServiceIntent.putExtra("message", "Do some meaningful work");
                startService(startIntentServiceIntent);
                break;
            case R.id.stop_intent_service_button:
                final Intent stopIntentServiceIntent = new Intent();
                stopIntentServiceIntent.setClass(getApplicationContext(), MyIntentService.class);
                stopService(stopIntentServiceIntent);
                break;
            case R.id.bind_service_button:
                bindWithBindableService();
                break;
            case R.id.unbind_service_button:
                unbindFromBindableService();
                break;
            case R.id.find_factorial_button:
                final int number =
                        Integer.parseInt(mFactorialInputNumberEditText.getText().toString());
                getFactorialFromService(number);
                break;
            default:
                Log.e(TAG, "View element not registered for on click event");
                break;
        }
    }

    private void bindWithBindableService() {
        final Intent intent = new Intent();
        intent.setClass(getApplicationContext(), BindableService.class);
        bindService(intent, mBindableServiceConnection, Context.BIND_AUTO_CREATE);
        isServiceBound = true;
        Log.i(TAG, "Service binded");
    }

    private void unbindFromBindableService() {
        if (isServiceBound) {
            unbindService(mBindableServiceConnection);
            Log.i(TAG, "Unbind from service");
            isServiceBound = false;
            return;
        }

        Log.e(TAG, "Service not bound currently. Please start the service if you wish to find" +
                " factorial values");
    }

    private void getFactorialFromService(final int number) {
        if (isServiceBound) {
            final int result = mBindableService.findFactorial(number);
            Toast.makeText(MainActivity.this, "Factorial of number " + number + " is: "
                    + result, Toast.LENGTH_SHORT).show();
        } else {
            Log.e(TAG, "Bind with the service to find factorial values");
        }
    }

    private ServiceConnection mBindableServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(final ComponentName name, final IBinder service) {
            BindableService.LocalBinder binder = (BindableService.LocalBinder) service;
            mBindableService = binder.getService();
            isServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(final ComponentName name) {
            Log.i(TAG, "onServiceDisconnected");
        }
    };
}
