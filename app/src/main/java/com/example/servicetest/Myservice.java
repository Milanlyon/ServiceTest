package com.example.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Random;

public class Myservice extends Service {
    private boolean mIsRandomGeneratorOn = true;
    private int mRandomNumber;
    private final int MIN=0;
    private final int MAX=100;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("pk", "Thread id: " + Thread.currentThread().getId() );
        new Thread(new Runnable() {
            @Override
            public void run() {
                startRandomNumberGenerator();
            }
        }).start();
        return START_STICKY;
    }
    private void startRandomNumberGenerator() {
        while (mIsRandomGeneratorOn) {
            try {
                Thread.sleep(1000);
                if (mIsRandomGeneratorOn) {
                    mRandomNumber = new Random().nextInt(MAX) + MIN;
                    Log.i("pk", "Thread id: " + Thread.currentThread().getId() + ", Random Number: " + mRandomNumber);
                }
            } catch (InterruptedException e) {
                Log.i("pk", "Thread Interrupted");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsRandomGeneratorOn = false;
    }
}
