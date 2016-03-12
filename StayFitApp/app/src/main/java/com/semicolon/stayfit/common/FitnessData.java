package com.semicolon.stayfit.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubhankar_roy on 3/12/2016.
 */
public class FitnessData {
    private final String email;
    private List<FitnessFieldData> fitnessFieldDataList = new ArrayList<>();

    public FitnessData(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public List<FitnessFieldData> getFitnessFieldDataList() {
        return fitnessFieldDataList;
    }

    public void setFitnessFieldDataList(List<FitnessFieldData> fitnessFieldDataList) {
        this.fitnessFieldDataList = fitnessFieldDataList;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append('\"').append("employeeEmail").append('\"').append(":").append('\"').append(email).append('\"').append(",");
        builder.append('\"').append("fitAppData").append('\"').append(":").append('[');
        for (FitnessFieldData fitnessFieldData: fitnessFieldDataList) {
            builder.append(fitnessFieldData).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(']');
        builder.append('}');
        return builder.toString();
    }
}
