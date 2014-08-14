package org.myWeather.web.domain;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class CalendarDaysColl implements Serializable{
	
	private static final long serialVersionUID = 7952806004880297338L;

	private int year, month, day;	
	
	private List<CalendarDayColl> calDaysCollList = new ArrayList<CalendarDayColl>();
	
	/*-------------------------------Getters and setters-------------------------------------------------------*/
	public List<CalendarDayColl> getCalDaysCollList() {
		return calDaysCollList;
	}	
	public void setCalDaysCollList(List<CalendarDayColl> calDaysCollList) {
		this.calDaysCollList = calDaysCollList;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	
	/*-------------------------------------------Functions----------------------------------------------------------*/
	public String getMonthForInt(int numMonth) {
        String month = "Jan";
        DateFormatSymbols dfs = new DateFormatSymbols(new Locale("en"));
        String[] months = dfs.getMonths();
        if (numMonth >= 0 && numMonth <= 11 ) {
            month = months[numMonth];
        }
        return month;
    }
}
