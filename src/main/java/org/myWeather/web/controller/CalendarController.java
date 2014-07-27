package org.myWeather.web.controller;

import org.myWeather.web.domain.Navigation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CalendarController {
	
	@Autowired
	private Navigation nav;
	
	//Spring security authentication
	private Authentication auth;
	
	@RequestMapping(value={"/calendar"}, method=RequestMethod.GET)
	public String getDefaultCalendar(Model model){
		nav.setSelectedPage("calendar");
		System.out.println(nav.getSelectedPage());
		model.addAttribute("navSelPage", nav);
		
		setAccesslevel(model);
		return "calendar";
	}
	public void setAccesslevel(Model model){
		auth = SecurityContextHolder.getContext().getAuthentication();
		String accessLevel= auth.getAuthorities().toString();
		System.out.println("User access level is: " + accessLevel);
		model.addAttribute("accessLevel", accessLevel);		
	}
}
