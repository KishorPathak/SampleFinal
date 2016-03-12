package com.semicolon.centaurs.util;

import java.io.Serializable;

public final class MessageConstants implements Serializable {
	private static final long serialVersionUID = 1L;
		
	public static final String STEP_COUNT = "com.google.step_count.delta";
	public static final String DISNTANCE_IN_METERS = "com.google.distance.delta";
	public static final String TOTAL_CALORIES = "com.google.calories.expended";
	public static final String CYCLING_SPEED = "com.google.speed.summary";
	public static final String REDIRECT_ERROR = "Can't redirect";
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_HR = "ROLE_HR";
	public static final String ROLE_DOC = "ROLE_DOC";
}
