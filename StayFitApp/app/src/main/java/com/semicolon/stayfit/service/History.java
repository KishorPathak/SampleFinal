package com.semicolon.stayfit.service;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResult;
import com.semicolon.stayfit.common.Display;
import com.semicolon.stayfit.common.FitnessData;
import com.semicolon.stayfit.common.FitnessFieldData;
import com.semicolon.stayfit.common.NetworkCall;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created By Shubhankar Roy
 */
public class History {
    private GoogleApiClient client;
    private Display display;
    private String email;
    private NetworkCall networkCall = new NetworkCall();

    private static final String TAG = History.class.getName();

    public History(GoogleApiClient client, Display display, String email) {
        this.client = client;
        this.display = display;
        this.email = email;
    }

    public void readDailyData() {
        readDailyDetailed(null);
    }

    public void readDailyTotalAllParams(ResultCallback<DataReadResult> resultCallback) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        long end = cal.getTimeInMillis();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        long start = cal.getTimeInMillis();
        final DateFormat outputDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        display.show("Showing Details for " + email);
        display.show("Reading Daily total: " + outputDateFormat.format(start) + " - " + outputDateFormat.format(end));

        if(resultCallback == null) {
            resultCallback = new HistoryResultCallback(display);
        }

        display.show("ResultCallback: " + resultCallback);
        DataReadRequest dataReadRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(start, end, TimeUnit.MILLISECONDS)
                .build();
        Fitness.HistoryApi.readData(client, dataReadRequest).setResultCallback(resultCallback);

        dataReadRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_DISTANCE_DELTA, DataType.AGGREGATE_DISTANCE_DELTA)
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(start, end, TimeUnit.MILLISECONDS)
                .build();
        Fitness.HistoryApi.readData(client, dataReadRequest).setResultCallback(resultCallback);

        dataReadRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_CALORIES_EXPENDED, DataType.AGGREGATE_CALORIES_EXPENDED)
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(start, end, TimeUnit.MILLISECONDS)
                .build();
        Fitness.HistoryApi.readData(client, dataReadRequest).setResultCallback(resultCallback);

        // TODO to be changed
        dataReadRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_SPEED, DataType.AGGREGATE_SPEED_SUMMARY)
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(start, end, TimeUnit.MILLISECONDS)
                .build();
        Fitness.HistoryApi.readData(client, dataReadRequest).setResultCallback(resultCallback);

        dataReadRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_ACTIVITY_SEGMENT, DataType.AGGREGATE_ACTIVITY_SUMMARY)
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(start, end, TimeUnit.MILLISECONDS)
                .build();
        Fitness.HistoryApi.readData(client, dataReadRequest).setResultCallback(resultCallback);

        /*Fitness.HistoryApi.readDailyTotal(client, DataType.TYPE_STEP_COUNT_DELTA).setResultCallback(resultCallback);
        Fitness.HistoryApi.readDailyTotal(client, DataType.TYPE_DISTANCE_DELTA).setResultCallback(resultCallback);
        Fitness.HistoryApi.readDailyTotal(client, DataType.TYPE_CALORIES_EXPENDED).setResultCallback(resultCallback);
        Fitness.HistoryApi.readDailyTotal(client, DataType.TYPE_SPEED).setResultCallback(resultCallback);
        Fitness.HistoryApi.readDailyTotal(client, DataType.TYPE_ACTIVITY_SEGMENT).setResultCallback(resultCallback);*/

        new AsyncTask<HistoryResultCallback, HistoryResultCallback, Void>() {

            @Override
            protected Void doInBackground(HistoryResultCallback... resultCallback) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                long end = cal.getTimeInMillis();
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                long start = cal.getTimeInMillis();

                List<FitnessFieldData> fitnessDataList = resultCallback[0].getFitnessDataList();
                display.log("Final List: " + fitnessDataList.toString());
                if (!fitnessDataList.isEmpty()) {
                    display.log("Final List: " + fitnessDataList.toString());
                    postData("employeeWorkoutStats", fitnessDataList);
                    display.log("Data Pushed...");
                } else {
                    display.log("Empty List: " + fitnessDataList.toString());
                }

                return null;
            }

        }.execute((HistoryResultCallback) resultCallback);
    }

    // TODO Future Scope -> To bifurcate on the basis of speed
    public void readDailyDetailed(ResultCallback<DataReadResult> resultCallback) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        long end = cal.getTimeInMillis();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        long start = cal.getTimeInMillis();
        final DateFormat outputDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        display.show("Showing Details for " + email);
        display.show("Reading Daily total: " + outputDateFormat.format(start) + " - " + outputDateFormat.format(end));

        if(resultCallback == null) {
            resultCallback = new HistoryResultCallback(display);
        }

        display.show("ResultCallback: " + resultCallback);
        DataReadRequest dataReadRequest = new DataReadRequest.Builder()
                .read(DataType.TYPE_STEP_COUNT_DELTA)
                .setTimeRange(start, end, TimeUnit.MILLISECONDS)
                .build();
        Fitness.HistoryApi.readData(client, dataReadRequest).setResultCallback(resultCallback);

        dataReadRequest = new DataReadRequest.Builder()
                .read(DataType.TYPE_DISTANCE_DELTA)
                .setTimeRange(start, end, TimeUnit.MILLISECONDS)
                .build();
        Fitness.HistoryApi.readData(client, dataReadRequest).setResultCallback(resultCallback);

        dataReadRequest = new DataReadRequest.Builder()
                .read(DataType.TYPE_CALORIES_EXPENDED)
                .setTimeRange(start, end, TimeUnit.MILLISECONDS)
                .build();
        Fitness.HistoryApi.readData(client, dataReadRequest).setResultCallback(resultCallback);

        // TODO to be changed
        dataReadRequest = new DataReadRequest.Builder()
                .read(DataType.TYPE_CYCLING_WHEEL_RPM)
                .setTimeRange(start, end, TimeUnit.MILLISECONDS)
                .build();
        Fitness.HistoryApi.readData(client, dataReadRequest).setResultCallback(resultCallback);

        dataReadRequest = new DataReadRequest.Builder()
                .read(DataType.TYPE_CYCLING_WHEEL_REVOLUTION)
                .setTimeRange(start, end, TimeUnit.MILLISECONDS)
                .build();
        Fitness.HistoryApi.readData(client, dataReadRequest).setResultCallback(resultCallback);

        new AsyncTask<HistoryResultCallback, HistoryResultCallback, Void>() {

            @Override
            protected Void doInBackground(HistoryResultCallback... resultCallback) {
                List<FitnessFieldData> fitnessDataList = resultCallback[0].getFitnessDataList();
                display.log("Final List: " + fitnessDataList.toString());
                if (!fitnessDataList.isEmpty()) {
                    display.log("Final List: " + fitnessDataList.toString());
                    postData("employeeWorkoutStats", fitnessDataList);
                    display.log("Data Pushed...");
                } else {
                    display.log("Empty List: " + fitnessDataList.toString());
                }

                return null;
            }

        }.execute((HistoryResultCallback) resultCallback);
    }

    public void readDataReadRequest(DataReadRequest readRequest, ResultCallback<DataReadResult> resultCallback) {
        if(readRequest == null) return;
        Fitness.HistoryApi.readData(client, readRequest).setResultCallback(resultCallback);
    }

    public void postData(String actionUrl, final List<FitnessFieldData> fitnessDataList) {
        try {
            FitnessData fitnessData = new FitnessData(email);
            fitnessData.setFitnessFieldDataList(fitnessDataList);
            Log.d(TAG, "postData: " + fitnessData);
            //String out = networkCall.postData(actionUrl, fitnessData.toString());
            //Log.d(TAG, out);
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage(), e);
        }
    }
}