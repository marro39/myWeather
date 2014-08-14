package org.myWeather.web.controller;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.myWeather.web.domain.CalendarDayColl;
import org.myWeather.web.domain.CalendarDaysColl;
import org.myWeather.web.domain.CalendarDaysColl2;
import org.myWeather.web.domain.Navigation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CalendarController {
	
	@Autowired
	private Navigation nav;
	
	@Autowired
	private CalendarDaysColl calDaysColl;
	
	//Spring security authentication
	private Authentication auth;
	
	/*---------------------------------------------Controller functions------------------------------------------------*/
	@RequestMapping(value={"/calendar"}, method=RequestMethod.GET)
	public String getDefaultCalendar(Model model){
		/*-----------------------------------CSS and Security configurations----------------*/
		nav.setSelectedPage("calendar");
		System.out.println(nav.getSelectedPage());
		model.addAttribute("navSelPage", nav);		
		setAccesslevel(model);		
		
		//Populate month of a calendar
		populateMonth(0);
		
		//A model attribute named calDaysColl containing a month of days with data is appended to model and shipped with calendar view		
		model.addAttribute("year", calDaysColl.getYear());
		model.addAttribute("month", calDaysColl.getMonthForInt(calDaysColl.getMonth()));
		model.addAttribute("intMonth", calDaysColl.getMonth());
		model.addAttribute("day", calDaysColl.getDay());
		
		
		model.addAttribute("calDaysColl", calDaysColl.getCalDaysCollList());
		
		return "calendar";
	}
	
	
	@RequestMapping(value={"/calendar/addSubMonth"}, method=RequestMethod.GET)
	public @ResponseBody CalendarDaysColl2 calendarAddSubOneMonth(@RequestParam("month") int month){		
		System.out.println("In function calendarAddOneMonth! Month is: " + month);
		
		//Populate month of a calendar		
		populateMonth(month);				
		
		CalendarDaysColl2 calDaysColl2 = new CalendarDaysColl2();
		calDaysColl2.setYear(calDaysColl.getYear());
		calDaysColl2.setMonth(calDaysColl.getMonth());
		calDaysColl2.setDay(calDaysColl.getDay());
		calDaysColl2.setMonthInText(calDaysColl.getMonthForInt(calDaysColl2.getMonth()));		
		calDaysColl2.setCalDaysData(calDaysColl.getCalDaysCollList());
		
		return calDaysColl2;
	}	
	
	/*----------------------------------------------Regular functions--------------------------------------------------*/
	public void populateMonth(int month){
		TimeZone timeZone = TimeZone.getTimeZone("Europe/Stockholm");
		Calendar calendar=Calendar.getInstance(timeZone);
		
		//Add or subtract wished steps of month
		calendar.add(Calendar.MONTH, + month);
		//Set calendar to day 1 of month
		calendar.set(Calendar.DAY_OF_MONTH, 1);	
		
		calDaysColl.setYear(calendar.get(Calendar.YEAR));
		calDaysColl.setMonth(calendar.get(Calendar.MONTH));
		calDaysColl.setDay(calendar.get(Calendar.DAY_OF_MONTH));
		
		System.out.println("Actual month: " + calendar.get(Calendar.MONTH));
		System.out.println("Day of month before switch: " + calendar.get(Calendar.DAY_OF_MONTH));
		System.out.println("Days in month are: " + calendar.getActualMaximum(Calendar.DAY_OF_MONTH));		
		
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {		
			case 2:{			
				//calendar.add(Calendar.DAY_OF_MONTH, -5);
				System.out.println("Case: 2 Day of month: " + calendar.get(Calendar.DAY_OF_MONTH));				
				break;
			}			
			case 3:{			
				calendar.add(Calendar.DAY_OF_MONTH, -1);
				System.out.println("Case: 3 Day of month: " + calendar.get(Calendar.DAY_OF_MONTH));				
				break;
			}
			case 4:{			
				calendar.add(Calendar.DAY_OF_MONTH, -2);
				System.out.println("Case: 4 Day of month: " + calendar.get(Calendar.DAY_OF_MONTH));				
				break;
			}
			case 5:{			
				System.out.println("Day of week is: " + calendar.get(Calendar.DAY_OF_WEEK));
				calendar.add(Calendar.DAY_OF_MONTH, -3);
				System.out.println("Case: 5 Day of month: " + calendar.get(Calendar.DAY_OF_MONTH));				
				break;
			}
			case 6:{			
				calendar.add(Calendar.DAY_OF_MONTH, -4);
				System.out.println("Case: 6 Day of month: " + calendar.get(Calendar.DAY_OF_MONTH));				
				System.out.println("Day of week: " + calendar.get(Calendar.DAY_OF_WEEK));
				break;
			}
			case 7:{			
				calendar.add(Calendar.DAY_OF_MONTH, -5);
				System.out.println("Case: 7 Day of month: " + calendar.get(Calendar.DAY_OF_MONTH));
				break;
			}
			case 1:{			
				calendar.add(Calendar.DAY_OF_MONTH, -6);
				System.out.println("Case: 1 Day of month: " + calendar.get(Calendar.DAY_OF_MONTH));				
				break;
			}
			default:
				break;
		}	
		System.out.println("Before for loop: " + calendar.get(Calendar.DAY_OF_MONTH));
		
		//Clear list before populating!
		calDaysColl.getCalDaysCollList().clear();
		
		for(int i=0;i<42;i++){			
			//Create days
			CalendarDayColl calDayColl = new CalendarDayColl(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			//Add day to list
			calDaysColl.getCalDaysCollList().add(calDayColl);
			System.out.println("-------------------------------------------------------");
			System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
			System.out.println("Year: " + calDayColl.getYear() + " Month: " + calDayColl.getMonth() + " Day: " + calDayColl.getDayOfMonth());
			calendar.add(Calendar.DAY_OF_MONTH, + 1);
		}
		System.out.println("Size of daylist: " + calDaysColl.getCalDaysCollList().size());
	}	
	
	public void setAccesslevel(Model model){
		auth = SecurityContextHolder.getContext().getAuthentication();
		String accessLevel= auth.getAuthorities().toString();
		System.out.println("User access level is: " + accessLevel);
		model.addAttribute("accessLevel", accessLevel);		
	}	
	
	/*------------------------------------------Appended model attributes------------------------------------------------------*/
	
	//NOT WORKING WELL WITH AUTOWIRED!!!
	/*
	@ModelAttribute("calDaysColl")
	public CalendarDaysColl getCalDaysColl(){
		return calDaysColl;
	}
	*/
}
