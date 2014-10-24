package org.myWeather.persistence.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.myWeather.persistence.domain.DayEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;



public interface DayEventRepository extends JpaRepository<DayEvent, Integer> {	
	
	//@Query("SELECT count(e) FROM org.myWeather.persistence.domain.DayEvent e WHERE trunc(e.dateFrom) = trunc(:date)")
	@Query("SELECT count(e) FROM org.myWeather.persistence.domain.DayEvent e WHERE e.dateFrom between :dateFrom AND :dateTo")
	public int countDayEvents(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
	
	@Query("SELECT e FROM org.myWeather.persistence.domain.DayEvent e WHERE e.dateFrom between :dateFrom AND :dateTo")
	public List<DayEvent> getDayEvents(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
}
