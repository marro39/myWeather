package org.myWeather.persistence.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.myWeather.persistence.domain.DayEvent;

public interface DayEventDAO {
	public void createDayEvent(DayEvent dayEvent);
	public int getEventsByDate(Date dateFrom, Date dateTo);
	
}
