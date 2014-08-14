package org.myWeather.web.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CalendarDaysColl2 implements Serializable{

	private static final long serialVersionUID = 3882057846830783410L;
	String monthInText;
	int year, month, day;	
	ArrayList<CalendarDayColl> calDaysData;
	
	public String getMonthInText() {
		return monthInText;
	}
	public void setMonthInText(String monthInText) {
		this.monthInText = monthInText;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<CalendarDayColl> getCalDaysData() {
		return calDaysData;
	}
	public void setCalDaysData(List<CalendarDayColl> list) {
		this.calDaysData = (ArrayList<CalendarDayColl>) list;
	}	
	
}
