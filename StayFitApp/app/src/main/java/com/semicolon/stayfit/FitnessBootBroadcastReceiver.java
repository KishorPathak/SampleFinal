package com.semicolon.stayfit;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.semicolon.stayfit.common.Constants;

import java.util.Calendar;

/**
 * Created by shubhankar_roy on 3/12/2016.
 */
public class FitnessBootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intentParam) {
        Intent intent = new Intent(context, FitnessBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis() , Constants.ONE_DAY_IN_MILLI_SECONDS, pendingIntent);
        //Toast.makeText(context, "Boot Alarm set in " + 1 + " day", Toast.LENGTH_LONG).show();
    }
}
