package com.semicolon.centaurs.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.semicolon.centaurs.model.EmployeeWorkoutStats;
import com.semicolon.centaurs.util.Queries;

public interface EmployeeWorkoutsStatsRepository extends JpaRepository<EmployeeWorkoutStats, Long>  {

	@Query(value = Queries.zoneQuery, nativeQuery = true)
	List<Object[]> getZones(@Param("from") String from,@Param("to") String to);
	

	
	@Query(value = Queries.getDailyZoneQuery, nativeQuery = true)
	List<Object[]> getDailyZones(@Param("from") String from,@Param("to") String to);
	
	
	
	@Query(value = Queries.getDailyCaloriesQuery, nativeQuery = true)
	List<Object[]> getDailyCalories(@Param("from") String from,@Param("to") String to,@Param("empId") int empId);
	

	@Query(value = Queries.getEmpDataQuery, nativeQuery = true)
	List<Object[]> getEmpData(@Param("from") String from,@Param("to") String to,@Param("zone") String zone);
}
