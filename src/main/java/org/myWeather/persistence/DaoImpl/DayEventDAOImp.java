package org.myWeather.persistence.DaoImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.myWeather.persistence.dao.DayEventDAO;
import org.myWeather.persistence.domain.DayEvent;
import org.myWeather.persistence.repository.DayEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DayEventDAOImp implements DayEventDAO{
	
	@Autowired
	DayEventRepository dayEventRepository;
	
	@Override	
	public void createDayEvent(DayEvent dayEvent) {
		DayEvent dayEvent1=dayEvent;
		dayEventRepository.save(dayEvent1);		
	}
	
	@Override	
	public int getEventsByDate(Date dateFrom, Date dateTo){		
		return dayEventRepository.countDayEvents(dateFrom, dateTo);	
	}
	
	@Override
	public List<DayEvent> getTodayEvents(Date dateFrom, Date dateTo){
		return dayEventRepository.getDayEvents(dateFrom, dateTo);
	}
	
	
	
}
