package com.semicolon.stayfit;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.semicolon.stayfit.common.Display;
import com.semicolon.stayfit.common.FitnessData;
import com.semicolon.stayfit.common.FitnessFieldData;
import com.semicolon.stayfit.common.NetworkCall;
import com.semicolon.stayfit.common.UserEmailFetcher;
import com.semicolon.stayfit.service.HistoryResultCallback;
import com.semicolon.stayfit.service.HistoryTotalResultCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by shubhankar_roy on 3/11/2016.
 */
public class FitnessDataService extends IntentService {

    private static final String TAG = FitnessDataService.class.getName();
    private GoogleApiClient mGoogleApiFitnessClient;
    private boolean mTryingToConnect = false;
    private NetworkCall networkCall = new NetworkCall();
    public FitnessDataService() {
        super("FitnessDataService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        buildFitnessClient();
        Log.d(TAG, "GoogleFitService created");
    }

    private void buildFitnessClient() {
        // Create the Google API Client
        //mGoogleApiFitnessClient = FitnessApiClient.getInstance().getClient();
        mGoogleApiFitnessClient = new GoogleApiClient.Builder(this)
                .addApi(Fitness.HISTORY_API)
                .addScope(Fitness.SCOPE_ACTIVITY_READ)
                .addScope(Fitness.SCOPE_LOCATION_READ)
                .addConnectionCallbacks(
                        new GoogleApiClient.ConnectionCallbacks() {

                            @Override
                            public void onConnected(Bundle bundle) {
                                Log.i(TAG, "Google Fit connected.");
                                mTryingToConnect = false;
                                Log.d(TAG, "Notifying the UI that we're connected.");
                                notifyUiFitConnected();

                            }

                            @Override
                            public void onConnectionSuspended(int i) {
                                // If your connection to the sensor gets lost at some point,
                                // you'll be able to determine the reason and react to it here.
                                mTryingToConnect = false;
                                if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {
                                    Log.i(TAG, "Google Fit Connection lost.  Cause: Network Lost.");
                                } else if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
                                    Log.i(TAG, "Google Fit Connection lost.  Reason: Service Disconnected");
                                }
                            }
                        }
                )
                .addOnConnectionFailedListener(
                        new GoogleApiClient.OnConnectionFailedListener() {
                            // Called whenever the API client fails to connect.
                            @Override
                            public void onConnectionFailed(ConnectionResult result) {
                                mTryingToConnect = false;
                                notifyUiFailedConnection(result);
                            }
                        }
                )
                .build();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(mGoogleApiFitnessClient == null) {
            Log.w(TAG, "Fit needs to be initialized first....");
            return;
        }
        if (!mGoogleApiFitnessClient.isConnected()) {
            mTryingToConnect = true;
            mGoogleApiFitnessClient.connect();

            //Wait until the service either connects or fails to connect
            while (mTryingToConnect) {
                try {
                    Thread.sleep(100, 0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (mGoogleApiFitnessClient.isConnected()) {
                Log.d(TAG, "Requesting steps from Google Fit");
                getStepsToday();
                /*postTodayData(new Display(TAG) {
                    @Override
                    public void show(String msg) {
                        log(msg);
                    }
                });*/

                Log.d(TAG, "Fit update complete.  Allowing Android to destroy the service.");
        } else {
            //Not connected
            Log.w(TAG, "Fit wasn't able to connect, so the request failed.");
        }
    }

    private void getStepsToday() {
        new AsyncTask<HistoryTotalResultCallback, Void, HistoryTotalResultCallback>() {

            @Override
            protected HistoryTotalResultCallback doInBackground(HistoryTotalResultCallback... resultCallback) {
                Fitness.HistoryApi.readDailyTotal(mGoogleApiFitnessClient, DataType.TYPE_STEP_COUNT_DELTA).setResultCallback(resultCallback[0]);
                Fitness.HistoryApi.readDailyTotal(mGoogleApiFitnessClient, DataType.TYPE_DISTANCE_DELTA).setResultCallback(resultCallback[0]);
                Fitness.HistoryApi.readDailyTotal(mGoogleApiFitnessClient, DataType.TYPE_CALORIES_EXPENDED).setResultCallback(resultCallback[0]);

                return resultCallback[0];
            }

            @Override
            protected void onPostExecute(HistoryTotalResultCallback historyResultCallback) {
                List<FitnessFieldData> fitnessDataList = historyResultCallback.getFitnessDataList();
                Log.d(TAG, "Final List: " + fitnessDataList.toString());
                if (!fitnessDataList.isEmpty()) {
                    Log.d(TAG, "Final List: " + fitnessDataList.toString());
                    postData("employeeWorkoutStats", fitnessDataList);
                    Log.d(TAG, "Data Pushed...");
                } else {
                    Log.d(TAG, "Empty List: " + fitnessDataList.toString());
                }
                if (mGoogleApiFitnessClient!= null && mGoogleApiFitnessClient.isConnected()) {
                    Log.d(TAG, "Disconecting Google Fit.");
                    mGoogleApiFitnessClient.disconnect();
                }
            }
        }.execute(new HistoryTotalResultCallback(new Display(TAG) {
            @Override
            public void show(String msg) {
                log(msg);
            }
        }));
    }

    private void postTodayData(final Display display) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        final long end = cal.getTimeInMillis();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        final long start = cal.getTimeInMillis();
        final DateFormat outputDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        String email = UserEmailFetcher.getEmail(this);
        display.show("Showing Details for " + email);
        display.show("Reading Daily total: " + outputDateFormat.format(start) + " - " + outputDateFormat.format(end));
        HistoryResultCallback resultCallback = new HistoryResultCallback(display);

        new AsyncTask<HistoryResultCallback, Void, HistoryResultCallback>() {

            @Override
            protected HistoryResultCallback doInBackground(HistoryResultCallback... resultCallback) {
                DataReadRequest dataReadRequest = new DataReadRequest.Builder()
                        .read(DataType.TYPE_STEP_COUNT_DELTA)
                        .setTimeRange(start, end, TimeUnit.MILLISECONDS)
                        .build();
                Fitness.HistoryApi.readData(mGoogleApiFitnessClient, dataReadRequest).setResultCallback(resultCallback[0]);

                dataReadRequest = new DataReadRequest.Builder()
                        .read(DataType.TYPE_DISTANCE_DELTA)
                        .setTimeRange(start, end, TimeUnit.MILLISECONDS)
                        .build();
                Fitness.HistoryApi.readData(mGoogleApiFitnessClient, dataReadRequest).setResultCallback(resultCallback[0]);

                dataReadRequest = new DataReadRequest.Builder()
                        .read(DataType.TYPE_CALORIES_EXPENDED)
                        .setTimeRange(start, end, TimeUnit.MILLISECONDS)
                        .build();
                Fitness.HistoryApi.readData(mGoogleApiFitnessClient, dataReadRequest).setResultCallback(resultCallback[0]);

                // TODO to be changed
                dataReadRequest = new DataReadRequest.Builder()
                        .read(DataType.TYPE_CYCLING_WHEEL_RPM)
                        .setTimeRange(start, end, TimeUnit.MILLISECONDS)
                        .build();
                Fitness.HistoryApi.readData(mGoogleApiFitnessClient, dataReadRequest).setResultCallback(resultCallback[0]);

                dataReadRequest = new DataReadRequest.Builder()
                        .read(DataType.TYPE_CYCLING_WHEEL_REVOLUTION)
                        .setTimeRange(start, end, TimeUnit.MILLISECONDS)
                        .build();
                Fitness.HistoryApi.readData(mGoogleApiFitnessClient, dataReadRequest).setResultCallback(resultCallback[0]);

                return resultCallback[0];
            }

            @Override
            protected void onPostExecute(HistoryResultCallback historyResultCallback) {
                List<FitnessFieldData> fitnessDataList = historyResultCallback.getFitnessDataList();
                Log.d(TAG, "Final List: " + fitnessDataList.toString());
                if (!fitnessDataList.isEmpty()) {
                    Log.d(TAG, "Final List: " + fitnessDataList.toString());
                    postData("employeeWorkoutStats", fitnessDataList);
                    Log.d(TAG, "Data Pushed...");
                } else {
                    Log.d(TAG, "Empty List: " + fitnessDataList.toString());
                }
                if (mGoogleApiFitnessClient!= null && mGoogleApiFitnessClient.isConnected()) {
                    Log.d(TAG, "Disconecting Google Fit.");
                    mGoogleApiFitnessClient.disconnect();
                }
            }
        }.execute((HistoryResultCallback) resultCallback);
    }

    public void postData(String actionUrl, final List<FitnessFieldData> fitnessDataList) {
        try {
            String email = UserEmailFetcher.getEmail(this);
            FitnessData fitnessData = new FitnessData(email);
            fitnessData.setFitnessFieldDataList(fitnessDataList);
            Log.d(TAG, "postData: " + fitnessData);
            if(!fitnessData.getFitnessFieldDataList().isEmpty()) {
                String out = networkCall.postData(actionUrl, fitnessData.toString());
                Log.d(TAG, out);
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage(), e);
        } /*catch (JSONException e) {
            Log.e(TAG, "Exception: " + e.getMessage(), e);
        }*/
    }

    private void notifyUiFitConnected() {
        // TODO Notification or Toast
        //Toast.makeText(this, "Google Fit Connected.", Toast.LENGTH_LONG).show();
    }

    private void notifyUiFailedConnection(ConnectionResult result) {
        // TODO Notification or Toast
        Log.d("FitnessDataService", "Result Error: " + result.getErrorMessage());
        Log.d("FitnessDataService", "Result Error: " + result.getErrorCode());
        //Toast.makeText(this, "Google Fit Connection failed.", Toast.LENGTH_LONG).show();
    }
}
