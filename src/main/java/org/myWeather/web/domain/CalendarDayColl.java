package org.myWeather.web.domain;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class CalendarDayColl implements Serializable{
	
	private static final long serialVersionUID = -934878081655994096L;
	
	private String weekDayOfMonth, title, description;
	private int year, month, dayOfMonth;
	
	/*---------------------------------------Contructor-----------------------------------------------------*/
	public CalendarDayColl(int year, int month, int day){
		this.year=year;
		this.month=month;
		this.dayOfMonth=day;
	}
	
	/*---------------------------------------Functions------------------------------------------------------*/	
	public String getWeekDayOfMonth() {
		return weekDayOfMonth;
	}
	public void setWeekDayOfMonth(String weekDayOfMonth) {
		this.weekDayOfMonth = weekDayOfMonth;
	}
	public String getTitle() {
		return title;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public int getDayOfMonth() {
		return dayOfMonth;
	}
	public void setDayOfMonth(int dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}
}
