package org.myWeather.web.domain;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
public class CalendarDayColl implements Serializable{
	
	private static final long serialVersionUID = -934878081655994096L;
	
	private String weekDayOfMonth;
	private int year;
	private int month;
	private int dayOfMonth;
	private int numberOfEvents;
	
	public int getNumberOfEvents() {
		return numberOfEvents;
	}
	public void setNumberOfEvents(int numberOfEvents) {
		this.numberOfEvents = numberOfEvents;
	}

	/*---------------------Handling add day event data-------------------------------------------------------*/
	@NotEmpty
	@NotNull
	//@Max(20)
	private String title;
	
	@NotEmpty
	@NotNull
	//@Max(10)
	private String ymdFrom, ymdTo;
	
	@NotEmpty
	@NotNull
	//@Max(5)
	private String hoursMinFrom, hoursMinTo;
	
	//@Max(200)
	private String description; 
	
	//@Max(30)
	private String location;		
	
	/*---------------------------------------Contructor-----------------------------------------------------*/
	public CalendarDayColl(int year, int month, int day){
		this.year=year;
		this.month=month;
		this.dayOfMonth=day;
	}
	/*---------------------------------------Contructor2----------------------------------------------------*/
	public CalendarDayColl(){}
	
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

	public String getYmdFrom() {
		return ymdFrom;
	}

	public void setYmdFrom(String ymdFrom) {
		this.ymdFrom = ymdFrom;
	}

	public String getYmdTo() {
		return ymdTo;
	}

	public void setYmdTo(String ymdTo) {
		this.ymdTo = ymdTo;
	}

	public String getHoursMinFrom() {
		return hoursMinFrom;
	}

	public void setHoursMinFrom(String hoursMinFrom) {
		this.hoursMinFrom = hoursMinFrom;
	}

	public String getHoursMinTo() {
		return hoursMinTo;
	}

	public void setHoursMinTo(String hoursMinTo) {
		this.hoursMinTo = hoursMinTo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
}
