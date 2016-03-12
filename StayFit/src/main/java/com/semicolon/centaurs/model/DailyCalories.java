package com.semicolon.centaurs.model;

public class DailyCalories {

	private String date;
	private double calories;
	private String zone;
	public DailyCalories() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DailyCalories(String date, double calories) {
		super();
		this.date = date;
		this.calories = calories;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getCalories() {
		return calories;
	}
	public void setCalories(double calories) {
		this.calories = calories;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
		
	
}
