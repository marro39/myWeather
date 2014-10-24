package org.myWeather.web.controller;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.myWeather.persistence.DaoImpl.DayEventDAOImp;
import org.myWeather.persistence.dao.DayEventDAO;
import org.myWeather.persistence.domain.DayEvent;
import org.myWeather.persistence.repository.DayEventRepository;
import org.myWeather.web.domain.CalendarDayColl;
import org.myWeather.web.domain.CalendarDaysColl;
import org.myWeather.web.domain.CalendarDaysColl2;
import org.myWeather.web.domain.Navigation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
	
	@Autowired	
	private DayEventDAOImp dayEventDAOImp;
	
	//Spring security authenticationn
	private Authentication auth;
	
	/*---------------------------------------------Controller functions------------------------------------------------*/
	/*---------------------------------------------Default calendar page-----------------------------------------------*/
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
	/*------------------------------Add or sub month and populate calender again---------------------------------------*/
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
	@RequestMapping(value={"/calendar/showEventsForSpecificDay"}, method=RequestMethod.GET)
	public @ResponseBody CalendarDayColl calendarShowDayEvents(@RequestParam("eventDate") Date eventDate ){
		
			//Date dateFrom=new SimpleDateFormat().parse(eventDate);
			@SuppressWarnings("deprecation")
			//Date eventsDate=new Date(eventDate);
			Date eventsDate=eventDate;
			TimeZone timeZone = TimeZone.getTimeZone("Europe/Stockholm");
			Calendar calendar=Calendar.getInstance(timeZone);
			calendar.setTime(eventsDate);
			//return calendar.get(Calendar.YEAR);
			
			
			Calendar calendar1=Calendar.getInstance(timeZone);
			calendar1.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			calendar1.set(Calendar.HOUR_OF_DAY, 0);
			calendar1.set(Calendar.MINUTE, 0);
			calendar1.set(Calendar.SECOND, 0);
			//calendar1.set(Calendar.DAY_OF_MONTH, 0);
			
			Calendar calendar2=Calendar.getInstance(timeZone);
			calendar2.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			calendar2.set(Calendar.HOUR_OF_DAY, 23);
			calendar2.set(Calendar.MINUTE, 59);
			calendar2.set(Calendar.SECOND, 59);
			
			CalendarDayColl calendarDayColl=new CalendarDayColl(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			List<DayEvent> dayEventList = dayEventDAOImp.getTodayEvents(calendar1.getTime(), calendar2.getTime());
			calendarDayColl.setDayEvents(dayEventList);
			
			
			return calendarDayColl;		
	}
	
	@RequestMapping(value={"/calendarAddDayEvent"}, method=RequestMethod.POST)
	public String addDayEvent(@Valid @ModelAttribute("calDayColl") CalendarDayColl calDayColl1, Model model, BindingResult bindingResult){
		
		if(bindingResult.hasErrors()){
			System.out.println("Error submitting day event data!");
			return "calendar";
		}	
		
		DayEvent dayEvent=new DayEvent();
		dayEvent.setUserId(1);
		dayEvent.setLocation(calDayColl1.getLocation());
		dayEvent.setTitle(calDayColl1.getTitle());
		if(!calDayColl1.getDescription().isEmpty()){
			dayEvent.setDescription(calDayColl1.getDescription());
		}
		
		System.out.println("\n---------------------------------------------------------------");
		System.out.println("Validating day event data worked!");
		System.out.println("Date from: " + calDayColl1.getYmdFrom());
		System.out.println("Time from: " + calDayColl1.getHoursMinFrom());
		System.out.println("Date to: " + calDayColl1.getYmdTo());
		System.out.println("Time to: " + calDayColl1.getHoursMinTo());
		System.out.println("Description: " + calDayColl1.getDescription());
		System.out.println("Title: " + calDayColl1.getTitle());
		System.out.println("Location: " + calDayColl1.getLocation());
		
		try {
			Date dateFrom=new SimpleDateFormat("yyyy-MM-d-k:m").parse(calDayColl1.getYmdFrom().toString() + "-" + calDayColl1.getHoursMinFrom().toLowerCase());
			TimeZone timeZone = TimeZone.getTimeZone("Europe/Stockholm");
			Calendar calendar=Calendar.getInstance(timeZone);
			calendar.setTime(dateFrom);
			
			dayEvent.setDateFrom(calendar.getTime());
			
			System.out.println(calendar.get(Calendar.YEAR));
			System.out.println(calendar.get(Calendar.MONTH));
			System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
			System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
			System.out.println(calendar.get(Calendar.MINUTE));
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error creating date from");
		}
		
		try {
			Date dateTo=new SimpleDateFormat("yyyy-MM-d-k:m").parse(calDayColl1.getYmdTo().toString() + "-" + calDayColl1.getHoursMinTo().toLowerCase());
			TimeZone timeZone1 = TimeZone.getTimeZone("Europe/Stockholm");
			Calendar calendar1=Calendar.getInstance(timeZone1);
			calendar1.setTime(dateTo);
			
			dayEvent.setDateTo(calendar1.getTime());
			
			System.out.println(calendar1.get(Calendar.YEAR));
			System.out.println(calendar1.get(Calendar.MONTH));
			System.out.println(calendar1.get(Calendar.DAY_OF_MONTH));
			System.out.println(calendar1.get(Calendar.HOUR_OF_DAY));
			System.out.println(calendar1.get(Calendar.MINUTE));
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error creating date to");
		}
		
		dayEventDAOImp.createDayEvent(dayEvent);
		
		return getDefaultCalendar(model);
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
			
			//Date from = new Date(calDayColl.getYear(),calDayColl.getMonth(),calDayColl.getDayOfMonth());
			//Date to = new Date(calDayColl.getYear(),calDayColl.getMonth(),calDayColl.getDayOfMonth());
			TimeZone timeZone1 = TimeZone.getTimeZone("Europe/Stockholm");
			Calendar calendar1=Calendar.getInstance(timeZone1);
			calendar1.set(calDayColl.getYear(), calDayColl.getMonth(), calDayColl.getDayOfMonth());
			calendar1.set(Calendar.HOUR_OF_DAY, 0);
			calendar1.set(Calendar.MINUTE, 0);
			calendar1.set(Calendar.SECOND, 0);
			//calendar1.set(Calendar.DAY_OF_MONTH, 0);
			
			Calendar calendar2=Calendar.getInstance(timeZone1);
			calendar2.set(calDayColl.getYear(), calDayColl.getMonth(), calDayColl.getDayOfMonth());
			calendar2.set(Calendar.HOUR_OF_DAY, 23);
			calendar2.set(Calendar.MINUTE, 59);
			calendar2.set(Calendar.SECOND, 59);
			//calendar2.set(Calendar.DAY_OF_MONTH, +2);
			
			//int count= dayEventDAOImp.getEventsByDate(new Date(calDayColl.getYear(),calDayColl.getMonth(),calDayColl.getDayOfMonth()));
			//int count= dayEventDAOImp.getEventsByDate(calendar1.getTime(),calendar2.getTime());
			int count;
			
			
			//Date from = new SimpleDateFormat("dd-mmm-yy").parse(calendar1.getTime().toString());
			//Date to = new SimpleDateFormat("dd-mmm-yy").parse(calendar2.getTime().toString());
			count = dayEventDAOImp.getEventsByDate(calendar1.getTime(),calendar2.getTime());
			calDayColl.setNumberOfEvents(count);
			
			
				//count = dayEventDAOImp.getEventsByDate(new SimpleDateFormat("dd/mmm/yy").parse(calendar1.getTime().toString()),new SimpleDateFormat("dd/mmm/yy").parse(calendar2.getTime().toString()));
			
			
			
			
			
			
			
			//List<DayEvent> listOne=dayEventDAOImp.getEventsByDate();
			//int count= dayEventDAOImp.getEventsByDate("Sikhall");
			//int count=listOne.size();
			//System.out.println("Amout events in day is: " + count);
			
			//calDayColl.setNumberOfEvents(count);
			
			//Add day to list
			calDaysColl.getCalDaysCollList().add(calDayColl);
			//System.out.println("-------------------------------------------------------");
			//System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
			//System.out.println("Year: " + calDayColl.getYear() + " Month: " + calDayColl.getMonth() + " Day: " + calDayColl.getDayOfMonth());
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
	@ModelAttribute("calDayColl")
	public CalendarDayColl getCalDayColl(){
		return new CalendarDayColl();
	}
}
