package com.semicolon.centaurs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.semicolon.centaurs.model.ZoneMaster;

public interface ZoneMasterRepository extends JpaRepository<ZoneMaster, Long>  {
	/*
	@Query("select z from ZoneMaster z where z.zoneStart>= :zoneStart and z.zoneEnd<= :zoneEnd")
	ZoneMaster findZoneBasedOnCaloriesAdherence(@Param("zoneStart") Float zoneStart, @Param("zoneEnd") Float zoneEnd);*/
}
