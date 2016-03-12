package com.semicolon.stayfit.common;

/**
 * Created by shubhankar_roy on 3/13/2016.
 */
public class FitnessRecommendation {
    private String email;
    private double distanceInMtr;
    private long stepsCount;
    private long calories;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getDistanceInMtr() {
        return distanceInMtr;
    }

    public void setDistanceInMtr(double distanceInMtr) {
        this.distanceInMtr = distanceInMtr;
    }

    public long getStepsCount() {
        return stepsCount;
    }

    public void setStepsCount(long stepsCount) {
        this.stepsCount = stepsCount;
    }

    public long getCalories() {
        return calories;
    }

    public void setCalories(long calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "FitnessRecommendation{" +
                "email='" + email + '\'' +
                ", distanceInMtr=" + distanceInMtr +
                ", stepsCount=" + stepsCount +
                ", calories=" + calories +
                '}';
    }
}
