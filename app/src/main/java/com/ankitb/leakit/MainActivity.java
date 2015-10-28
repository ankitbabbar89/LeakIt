package com.ankitb.leakit;

import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity implements MainFragment.TaskCallbacks {
    public static final String TAG = "LeakIt";
    MyAsyncTask mAsyncTask;
    IndependentAsynctask independentAsynctask;
    MainFragment mFragment;
    private static final String TAG_FRAGMENT = "tag_fragment";
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewAsyncTask();
                Snackbar.make(view, "Started New AsycnTask", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        FragmentManager fm = getFragmentManager();
        mFragment = (MainFragment) fm.findFragmentByTag(TAG_FRAGMENT);
        if(mFragment == null){
            mFragment = new MainFragment();
            fm.beginTransaction().add(mFragment,TAG_FRAGMENT).commit();
        }

        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setProgress(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        if(mAsyncTask != null) {
            Log.d(TAG, "onDestroy : cancelling Task");
            mAsyncTask.cancel(true);
        }
        if(independentAsynctask != null)
            independentAsynctask.cancel(true);
        super.onDestroy();

    }

    public void startNewAsyncTask(){
//       mAsyncTask = new MyAsyncTask();
//        mAsyncTask.execute();
        Log.d(TAG,"startNewAsyncTask");
        independentAsynctask = new IndependentAsynctask();
        independentAsynctask.execute();
    }

    @Override
    public void onPreExecute() {
        Log.d(TAG,"onPreExecute()");
    }

    @Override
    public void onPostExecute() {
        Log.d(TAG,"onPostExecute()");
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onCancelled() {
        Log.d(TAG,"onCancelled()");
        mProgressBar.setVisibility(View.GONE);

    }

    @Override
    public void updateProgress(int percent) {
        Log.d(TAG,"updateProgress() : " + percent);
        mProgressBar.setProgress(percent);

    }

    class MyAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            Log.d(TAG,"doInBackground");
            SystemClock.sleep(10000);
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.d(TAG,"cancelled");
        }
    }
}
