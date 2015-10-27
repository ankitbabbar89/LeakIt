package com.ankitb.leakit;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;

/**
 * Created by Ankit on 27/10/15.
 */
public class MainFragment extends Fragment {

    interface TaskCallbacks{
        void onPreExecute();
        void onPostExecute();
        void onCancelled();
        void updateProgress(int percent);
    }

    private TaskCallbacks mCallbacks;
    private MyFragmentTask mTask;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (TaskCallbacks) activity;
    }

    @Override
    public void setRetainInstance(boolean retain) {
        super.setRetainInstance(retain);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        mTask = new MyFragmentTask();
        mTask.execute();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    class MyFragmentTask extends AsyncTask<Void,Integer,Void>{
        @Override
        protected Void doInBackground(Void... params) {
            for(int i=0; !isCancelled() && i<100; ++i){
                SystemClock.sleep(100);
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(mCallbacks != null)
                mCallbacks.onPostExecute();
        }

        @Override
        protected void onPreExecute() {
            if(mCallbacks != null)
                mCallbacks.onPreExecute();
        }

        @Override
        protected void onCancelled() {
            if(mCallbacks != null)
                mCallbacks.onCancelled();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if(mCallbacks != null)
                mCallbacks.updateProgress(values[0]);
        }
    }
}
