package com.semicolon.stayfit;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {
    private static final String TAG = SplashScreen.class.getName();
    private static final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {

                @Override
                protected Boolean doInBackground(Void... params) {
                    ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    Log.d(TAG, "Result: " + result);
                    if (result) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, SPLASH_TIME_OUT);
                        Log.d(TAG, "Finish Success...");
                    } else {
                        new AlertDialog.Builder(SplashScreen.this)
                                .setTitle("Error")
                                .setMessage("Your Internet Connection is not available at the moment. Please try again later.")
                                .setPositiveButton(android.R.string.ok, null)
                                .show();
                        Log.d(TAG, "Finish Error...");
                        Toast.makeText(getApplicationContext(),"Internet Connection Unavailable",Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            };

            task.execute();
        } catch(Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage(), e);
        }
    }

}
