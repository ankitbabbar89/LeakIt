package com.ankitb.leakit;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import java.util.Random;

/**
 * Separate AsyncTask, so that we dont hold reference to the Activity.
 * This wouldnot be desirable in all scenarios as we will have to communicate with
 * our main activity with the task result at some point.
 */
public class IndependentAsynctask extends AsyncTask<Void,Void,Void> {

    @Override
    protected Void doInBackground(Void... params) {
        Random random = new Random();
        String name = "Thread : "+random.nextInt();
        Thread.currentThread().setName(name);
        Log.d(MainActivity.TAG, "doInBackground : " + Thread.currentThread().getName());
      //  SystemClock.sleep(10000);
        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();

        Log.d(MainActivity.TAG,"cancelled :"+Thread.currentThread().getName());
    }
}
