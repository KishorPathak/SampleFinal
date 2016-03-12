package com.semicolon.stayfit.service;

import android.util.Log;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.result.DataReadResult;
import com.semicolon.stayfit.common.Display;
import com.semicolon.stayfit.common.FitnessFieldData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by shubhankar_roy on 3/11/2016.
 */
public class HistoryResultCallback implements ResultCallback<DataReadResult> {
    final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");// SimpleDateFormat.getDateInstance();
    final DateFormat outputDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    private int counter = 0;

    private List<FitnessFieldData> fitnessDataList = new ArrayList<>();
    private Display display;

    public HistoryResultCallback(Display display) {
        this.display = display;
    }

    @Override
    public void onResult(DataReadResult dataReadResult) {
        counter++;
        try {
            if (dataReadResult.getBuckets().size() > 0) {
                display.show("DataSet.size(): "
                        + dataReadResult.getBuckets().size());
                for (Bucket bucket : dataReadResult.getBuckets()) {
                    List<DataSet> dataSets = bucket.getDataSets();
                    for (DataSet dataSet : dataSets) {
                        if (!dataSet.getDataPoints().isEmpty())
                            //display.show("dataSet.dataType: " + dataSet.getDataType().getName());

                            for (DataPoint dp : dataSet.getDataPoints()) {
                                fitnessDataList.add(describeDataPoint(dp, outputDateFormat));
                            }
                    }
                }
            } else if (dataReadResult.getDataSets().size() > 0) {
                display.show("dataSet.size(): " + dataReadResult.getDataSets().size());
                for (DataSet dataSet : dataReadResult.getDataSets()) {
                    if (!dataSet.getDataPoints().isEmpty())
                        //display.show("dataType: " + dataSet.getDataType().getName());

                        for (DataPoint dp : dataSet.getDataPoints()) {
                            fitnessDataList.add(describeDataPoint(dp, outputDateFormat));
                        }
                }
            }
        } finally {
            Log.d(HistoryResultCallback.class.getName(), "onResult: decrement()" + counter);
            counter--;
        }
    }

    public FitnessFieldData describeDataPoint(DataPoint dp, DateFormat dateFormat) {
        FitnessFieldData fitnessData = new FitnessFieldData();
        fitnessData.setStartDate(dateFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)));
        fitnessData.setEndDate(dateFormat.format(dp.getEndTime(TimeUnit.MILLISECONDS)));
        fitnessData.setDataType(dp.getDataType().getName());

        StringBuilder msg = new StringBuilder("dataPoint: ");
        msg.append("type: ").append(dp.getDataType().getName()).append("\n");
        msg.append(", range: [").append(dateFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS))).append("-").append(dateFormat.format(dp.getEndTime(TimeUnit.MILLISECONDS))).append("]\n");
        msg.append(", fields: [");
        StringBuilder fieldData = new StringBuilder();
        HashMap<String, String> fieldValues = new HashMap<>();
        fitnessData.setFieldValues(fieldValues);
        for(Field field : dp.getDataType().getFields()) {
            fieldData.append(field.getName()).append("=").append(dp.getValue(field)).append(" ");
            fieldValues.put(field.getName(), dp.getValue(field).toString());
        }
        msg.append(fieldData.toString()).append("]");
        //display.show(msg.toString());
        //display.show(fitnessData.toString());
        //postData(dp.getDataType().getName(), fieldData.toString());
        return fitnessData;
    }

    public List<FitnessFieldData> getFitnessDataList() {
        int waitCounter = 10;
        while(counter > 0 && waitCounter-- > 0) {
            try {
                Thread.sleep(1000);
                display.log(fitnessDataList.toString());
                Log.d("HistoryCallback", "Waiting for results... " + counter);
            } catch (InterruptedException e) {
                Log.e("HistoryCallback", "Exception", e);
            }
        }
        return fitnessDataList;
    }
}
