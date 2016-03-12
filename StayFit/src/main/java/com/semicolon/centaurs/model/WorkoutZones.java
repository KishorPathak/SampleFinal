package com.semicolon.centaurs.model;

public class WorkoutZones {
	
	private String name;
	private String y;
	public WorkoutZones()
	{}
	public WorkoutZones(String name) {
		super();
		this.name = name;
		
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public WorkoutZones(String name, String y) {
		super();
		this.name = name;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "WorkoutZones [name=" + name + ", y=" + y + "]";
	}
	
}