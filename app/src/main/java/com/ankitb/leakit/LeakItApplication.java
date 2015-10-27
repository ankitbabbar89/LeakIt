package com.ankitb.leakit;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Ankit on 27/10/15.
 */
public class LeakItApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
