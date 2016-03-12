package com.semicolon.centaurs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author kishor_pathak
 *
 */
@Entity
@Table(name = "zones")
public class ZoneMaster {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="zone_id")
    private Long zoneId;
    
	@Column(name="zone_start")
	private Float zoneStart;
	
	@Column(name="zone_end")
	private Float zoneEnd;
	
	@Column(name="zone_desc")
	private String zoneDesc;

	public Long getZoneId() {
		return zoneId;
	}

	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}

	public Float getZoneStart() {
		return zoneStart;
	}

	public void setZoneStart(Float zoneStart) {
		this.zoneStart = zoneStart;
	}

	public Float getZoneEnd() {
		return zoneEnd;
	}

	public void setZoneEnd(Float zoneEnd) {
		this.zoneEnd = zoneEnd;
	}

	public String getZoneDesc() {
		return zoneDesc;
	}

	public void setZoneDesc(String zoneDesc) {
		this.zoneDesc = zoneDesc;
	}
	
}
