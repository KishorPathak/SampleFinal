package com.semicolon.stayfit.service;

import android.util.Log;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.result.DailyTotalResult;
import com.semicolon.stayfit.common.Display;
import com.semicolon.stayfit.common.FitnessFieldData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by shubhankar_roy on 3/12/2016.
 */
public class HistoryTotalResultCallback implements ResultCallback<DailyTotalResult> {
    final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");// SimpleDateFormat.getDateInstance();
    final DateFormat outputDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    private int counter = 0;

    private List<FitnessFieldData> fitnessDataList = new ArrayList<>();
    private Display display;

    public HistoryTotalResultCallback(Display display) {
        this.display = display;
        initValidFieldList();
    }

    public Map<String, String> validFieldsMap = new LinkedHashMap<>();

    private void initValidFieldList() {
        validFieldsMap.put("com.google.step_count.delta", "steps");
        validFieldsMap.put("com.google.distance.delta", "distance");
        validFieldsMap.put("com.google.calories.expended", "calories");
        validFieldsMap.put("com.google.speed.summary", "average");
        validFieldsMap.put("com.google.activity.summary", "average");
    }

    public FitnessFieldData describeDataPoint(DataPoint dp, DateFormat dateFormat) {
        FitnessFieldData fitnessFieldData = new FitnessFieldData();
        fitnessFieldData.setStartDate(dateFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)));
        fitnessFieldData.setEndDate(dateFormat.format(dp.getEndTime(TimeUnit.MILLISECONDS)));
        fitnessFieldData.setDataType(dp.getDataType().getName());
        String fieldName = validFieldsMap.get(dp.getDataType().getName());
        if(fieldName != null) {
            HashMap<String, String> fieldValues = new HashMap<>();
            fitnessFieldData.setFieldValues(fieldValues);
            for (Field field : dp.getDataType().getFields()) {
                if(fieldName.equals(field.getName()))
                    fieldValues.put(field.getName(), dp.getValue(field).toString());
            }
            if(fieldValues.isEmpty())
                return null;
            return fitnessFieldData;
        }
        return null;
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

    @Override
    public void onResult(DailyTotalResult totalResult) {
        counter++;
        try {
            if (totalResult.getStatus().isSuccess()) {
                DataSet totalSet = totalResult.getTotal();
                if (!totalSet.getDataPoints().isEmpty())
                    //display.show("dataType: " + dataSet.getDataType().getName());

                    for (DataPoint dp : totalSet.getDataPoints()) {
                        FitnessFieldData fitnessFieldData = describeDataPoint(dp, outputDateFormat);
                        if(fitnessFieldData != null) {
                            fitnessDataList.add(fitnessFieldData);
                        }
                    }
            } else {
                // handle failure
            }
        } finally {
            counter--;
        }
    }
}
