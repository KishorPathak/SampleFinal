package com.semicolon.stayfit.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by shubhankar_roy on 3/12/2016.
 */
public class FitnessFieldData {
    private String dataType;
    private String startDate;
    private String endDate;
    private long totalTimeTaken;
    private Map<String, String> fieldValues;

    public FitnessFieldData() {
        fieldValues = new HashMap<>();
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Map<String, String> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(Map<String, String> fieldValues) {
        this.fieldValues = fieldValues;
    }

    public long getTotalTimeTaken() {
        return totalTimeTaken;
    }

    public void setTotalTimeTaken(long totalTimeTaken) {
        this.totalTimeTaken = totalTimeTaken;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append('\"').append("dataType").append('\"').append(":").append('\"').append(dataType).append('\"').append(",");
        builder.append('\"').append("startDate").append('\"').append(":").append('\"').append(startDate).append('\"').append(",");
        builder.append('\"').append("endDate").append('\"').append(":").append('\"').append(endDate).append('\"').append(",");
        builder.append('\"').append("totalTimeTaken").append('\"').append(":").append('\"').append(totalTimeTaken).append('\"').append(",");
        builder.append('\"').append("fieldValues").append('\"').append(":").append('{');
        Iterator<Map.Entry<String, String>> iterator = fieldValues.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            builder.append('\"').append(entry.getKey()).append('\"').append(":").append('\"').append(entry.getValue()).append('\"').append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append('}');
        builder.append('}');
        return builder.toString();
    }
}
