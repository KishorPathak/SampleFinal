package com.semicolon.centaurs.model;

public class DailyWorkOutZones {

	private String date;
	private String name;
	private String count;
	public DailyWorkOutZones(String date, String name, String count) {
		super();
		this.date = date;
		this.name = name;
		this.count = count;
	}
	public DailyWorkOutZones() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
			
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("{");
		str.append('\"').append("date").append('\"').append(':').append('\"').append(date).append('\"').append(',');
		str.append('\"').append("name").append('\"').append(':').append('\"').append(name).append('\"').append(',');
		str.append('\"').append("count").append('\"').append(':').append('\"').append(count).append('\"').append('}');
		return str.toString();
	}
}
