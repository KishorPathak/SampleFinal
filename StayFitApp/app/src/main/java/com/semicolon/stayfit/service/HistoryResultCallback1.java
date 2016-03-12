package com.semicolon.stayfit.service;

import android.util.Log;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.semicolon.stayfit.common.FitnessFieldData;

/**
 * Created by shubhankar_roy on 3/12/2016.
 */
public class HistoryResultCallback1<T extends Result> implements ResultCallback<T> {
    private static final String TAG = HistoryResultCallback1.class.getName();
    int requestCounter = 0;
    protected FitnessFieldData fitnessFieldData;

    public HistoryResultCallback1(FitnessFieldData fitnessFieldData) {
        this.fitnessFieldData = fitnessFieldData;
    }

    @Override
    public void onResult(T result) {

    }

    public void postResults(String action) {

    }

    public final void waitAndPostResults(final String action) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int waitCounter = 30;
                while(requestCounter > 0 && waitCounter-- > 0) {
                    try {
                        Log.d(TAG, "Waiting for result.");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "Exception in sleeping", e);
                    }
                }
                postResults(action);
            }
        }).start();
    }

    public void incrementCounter() {
        requestCounter++;
    }

    public void decrementCounter() {
        requestCounter--;
    }

    public FitnessFieldData getFitnessFieldData() {
        return fitnessFieldData;
    }
}
