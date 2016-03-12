package com.semicolon.stayfit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by shubhankar_roy on 3/12/2016.
 */
public class FitnessBroadcastReceiver extends BroadcastReceiver {


    @Override

    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_SHUTDOWN.equalsIgnoreCase(intent.getAction())) {
            Log.i("FitBroadcastReceiver", "Shutdown event");
            // database operation
        } else {
            context.startService(new Intent(context, FitnessDataService.class));
        }

    }
}