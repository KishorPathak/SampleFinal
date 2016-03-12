package com.semicolon.centaurs.util;

public class Queries {
	public static final String zoneQuery="select a.zone_desc, count(a.from_period) Emp_Cnt from"+
			"(   "+
			"select  overall_adh, from_period, zone_desc"+
			"  from emp_workout_stats s where from_period between str_to_date(:from,'%m/%d/%Y') and str_to_date(:to,'%m/%d/%Y')"+
			"  ) a  group by a.zone_desc"; 
	
	public static final String getDailyZoneQuery="SELECT DATE_FORMAT(from_period, '%m/%d/%Y') from_period, zone_desc, COUNT(emp_id) as 'cnt'"+
			" FROM emp_workout_stats s WHERE "+
			" from_period BETWEEN STR_TO_DATE(:from,'%m/%d/%Y') AND STR_TO_DATE(:to,'%m/%d/%Y')"+
			" GROUP BY from_period, zone_desc ORDER BY 1";
	
	public static final String getDailyCaloriesQuery="SELECT DATE_FORMAT(from_period, '%m/%d/%Y'), calories, zone_desc"+
			" FROM emp_workout_stats s WHERE"+
			" from_period BETWEEN STR_TO_DATE(:from,'%m/%d/%Y') AND STR_TO_DATE(:to,'%m/%d/%Y')"+
			" AND emp_id= :empId "+
			" ORDER BY 1";
	
	
	public static final String getEmpDataQuery="SELECT  em.emp_id,em.emp_name,ws.calories FROM emp_workout_stats ws"+
			"  INNER JOIN emp_mst em ON ws.emp_id=em.emp_id"+
			"  WHERE ws.from_period BETWEEN STR_TO_DATE(:from,'%m/%d/%Y') AND STR_TO_DATE(:to,'%m/%d/%Y')"+
			"  AND ws.zone_desc=:zone";
}
