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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by shubhankar_roy on 3/12/2016.
 */
public class DetailedHistoryResultCallback implements ResultCallback<DataReadResult> {
    private int counter = 0;
    private String startDate;
    private String endDate;

    private List<FitnessFieldData> fitnessDataList = new ArrayList<>();
    private Display display;

    public DetailedHistoryResultCallback(Display display, String startDate, String endDate) {
        this.display = display;
        this.startDate = startDate;
        this.endDate = endDate;
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
                        //display.show("dataType: " + dataSet.getDataType().getName() + " : Points -" + dataSet.getDataPoints().size());
                        if (!dataSet.getDataPoints().isEmpty()) {
                            StringBuilder builder = new StringBuilder("dataType: ");
                            builder.append(dataSet.getDataType().getName()).append(" - ");
                            FitnessFieldData fitnessFieldData = new FitnessFieldData();
                            fitnessFieldData.setStartDate(startDate);
                            fitnessFieldData.setEndDate(endDate);
                            fitnessFieldData.setDataType(dataSet.getDataType().getName());
                            Map<String, String> fieldValues = new HashMap<>();
                            fitnessFieldData.setFieldValues(fieldValues);

                            for (DataPoint dp : dataSet.getDataPoints()) {
                                describeDataPoint(dp, fieldValues, builder);
                            }
                            display.show(builder.toString());
                            fitnessDataList.add(fitnessFieldData);
                        }
                    }
                }
            } else if (dataReadResult.getDataSets().size() > 0) {
                display.show("dataSet.size(): " + dataReadResult.getDataSets().size());
                for (DataSet dataSet : dataReadResult.getDataSets()) {
                    //display.show("dataType: " + dataSet.getDataType().getName() + " : Points -" + dataSet.getDataPoints().size());
                    if (!dataSet.getDataPoints().isEmpty()) {
                        StringBuilder builder = new StringBuilder("dataType: ");
                        builder.append(dataSet.getDataType().getName()).append(" - ");
                        FitnessFieldData fitnessFieldData = new FitnessFieldData();
                        fitnessFieldData.setStartDate(startDate);
                        fitnessFieldData.setEndDate(endDate);
                        fitnessFieldData.setDataType(dataSet.getDataType().getName());
                        Map<String, String> fieldValues = new HashMap<>();
                        fitnessFieldData.setFieldValues(fieldValues);
                        long totalTimeTaken = 0;
                        for (DataPoint dp : dataSet.getDataPoints()) {
                            totalTimeTaken += dp.getEndTime(TimeUnit.SECONDS) - dp.getStartTime(TimeUnit.SECONDS);
                            describeDataPoint(dp, fieldValues, builder);
                        }
                        fitnessFieldData.setTotalTimeTaken(totalTimeTaken);
                        display.show(builder.toString());
                        fitnessDataList.add(fitnessFieldData);
                    }
                }
            }
        } finally {
            counter--;
        }
    }

    public void describeDataPoint(DataPoint dp, Map<String, String> fieldValues, StringBuilder builder) {
        for(Field field : dp.getDataType().getFields()) {
            String currentValue = fieldValues.get(field.getName());
            long timeTaken = dp.getEndTime(TimeUnit.SECONDS) - dp.getStartTime(TimeUnit.SECONDS);
            if(currentValue == null) {
                currentValue = dp.getValue(field).toString();
                fieldValues.put(field.getName(), currentValue);
                // Log per second consumption
                builder.append(currentValue).append("-").append(Double.valueOf(currentValue)/timeTaken).append(" per sec\t");
            } else {
                String value = dp.getValue(field).toString();
                Double outputVal = Double.valueOf(currentValue) + Double.valueOf(value);
                fieldValues.put(field.getName(), outputVal.toString());
                // Log per second consumption
                builder.append(value).append("-").append(Double.valueOf(currentValue)/timeTaken).append(" per sec\t");
            }
        }
        //builder.deleteCharAt(builder.length() - 1);
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
