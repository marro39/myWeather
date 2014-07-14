package org.myWeather.web.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.validation.Valid;

import org.myWeather.web.domain.Navigation;
import org.myWeather.web.domain.WeatherData;
import org.myWeather.web.domain.WeatherDataCollection;
import org.myWeather.web.domain.WeatherLocShort;
import org.myWeather.web.domain.WeatherLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.ArrayUtils;

@Controller
public class HomeController {

	@Autowired
	private WeatherDataCollection myWeatherDataCollection;
	@Autowired 
	private Navigation navSelPage;
	
	RestTemplate restTemplate;
	String link;	
	URI linkUri;
	
	//For medhod getSelectedWeather
	private String country,district, city;
	
	/*--------------------------------------------DEFAULT FUNCTION TO RETURN HOME PAGE------------------------------------*/
	@RequestMapping(value="", method=RequestMethod.GET)	
	public String getHomePage(Model model){		
		
		@SuppressWarnings("rawtypes")
		List<LinkedHashMap> weatherDataMapList = getWeatherDataFromDefCity("Sweden","Västra_Götaland","Vänersborg");
		System.out.println("Size of WeatherData: " + weatherDataMapList.size());		
		System.out.println(weatherDataMapList);
		
		//Clear WeatherDataCollection before filling it!
		myWeatherDataCollection.clearWeatherDataCollList();
		
		//setWeatherObjPutInCollection();
		for (@SuppressWarnings("rawtypes") LinkedHashMap linkedHashMap : weatherDataMapList) {
			WeatherData weatherDataObj = new WeatherData();
			weatherDataObj.setTimeFrom((String)linkedHashMap.get("timeFrom"));
			weatherDataObj.setTimeTo((String)linkedHashMap.get("timeTo"));
			weatherDataObj.setWeatherDescription((String)linkedHashMap.get("weatherDescription"));
			weatherDataObj.setPrecipitation((String)linkedHashMap.get("precipitation"));
			weatherDataObj.setWindDirection((String)linkedHashMap.get("windDirection"));
			weatherDataObj.setWindSpeed((String)linkedHashMap.get("windSpeed"));
			weatherDataObj.setWindSpeedDescription((String)linkedHashMap.get("windSpeedDescription"));
			weatherDataObj.setTemperature((String)linkedHashMap.get("temperature"));
			weatherDataObj.setPressureUnit((String)linkedHashMap.get("pressureUnit"));
			weatherDataObj.setPressureValue((String)linkedHashMap.get("pressureValue"));
			weatherDataObj.setCity((String)linkedHashMap.get("city"));
			weatherDataObj.setCountry((String)linkedHashMap.get("country"));
			weatherDataObj.setTimeZone((String)linkedHashMap.get("timeZone"));
			System.out.println("WeatherDataobject created! Temp is: ");
			System.out.println(weatherDataObj.getTemperature());
			myWeatherDataCollection.setWeatherDataObject(weatherDataObj);
		}
		System.out.println("Weatherdatacollection is set! Size is: ");
		System.out.println(myWeatherDataCollection.getSizeOfWeatherArray());
		
		//Set active page for css
		navSelPage.setSelectedPage("Home");	
		System.out.println(navSelPage.getSelectedPage());
		model.addAttribute("navSelPage",navSelPage);
		return "home";			
	}
	
	
	
	
	
	/*-----------------------------FUNCTION TO HANDLE WISHED TYPED WEATHER LOCATION--------------------------------------------*/
	@RequestMapping(value="/typed", method=RequestMethod.POST)	
	public String getTypedWeather(@Valid @ModelAttribute("weatherLocation") WeatherLocation weatherLocation1, BindingResult bindingResult,Model model){
		System.out.println("AT GETTYPEDWEATHER!");
		if(bindingResult.hasErrors()){
			System.out.println("ERROR ON VALIDATING WEATHERLOCATION");
			return "home";
		}
		System.out.println("VALIDATING WEATHERLOCATION WORKED");
		System.out.println("Country is: " + weatherLocation1.getCountry());
		System.out.println("District is: " + weatherLocation1.getDistrict());
		System.out.println("City is: " + weatherLocation1.getCity());
		
		
		@SuppressWarnings("rawtypes")
		List<LinkedHashMap> weatherDataMapList = getWeatherDataFromDefCity(weatherLocation1.getCountry(),weatherLocation1.getDistrict(),weatherLocation1.getCity());
		System.out.println("Size of WeatherData: " + weatherDataMapList.size());		
		System.out.println(weatherDataMapList);
		
		//Clear WeatherDataCollection before filling it!
		myWeatherDataCollection.clearWeatherDataCollList();
		
		//setWeatherObjPutInCollection();
		for (@SuppressWarnings("rawtypes") LinkedHashMap linkedHashMap : weatherDataMapList) {
			WeatherData weatherDataObj = new WeatherData();
			weatherDataObj.setTimeFrom((String)linkedHashMap.get("timeFrom"));
			weatherDataObj.setTimeTo((String)linkedHashMap.get("timeTo"));
			weatherDataObj.setWeatherDescription((String)linkedHashMap.get("weatherDescription"));
			weatherDataObj.setPrecipitation((String)linkedHashMap.get("precipitation"));
			weatherDataObj.setWindDirection((String)linkedHashMap.get("windDirection"));
			weatherDataObj.setWindSpeed((String)linkedHashMap.get("windSpeed"));
			weatherDataObj.setWindSpeedDescription((String)linkedHashMap.get("windSpeedDescription"));
			weatherDataObj.setTemperature((String)linkedHashMap.get("temperature"));
			weatherDataObj.setPressureUnit((String)linkedHashMap.get("pressureUnit"));
			weatherDataObj.setPressureValue((String)linkedHashMap.get("pressureValue"));
			weatherDataObj.setCity((String)linkedHashMap.get("city"));
			weatherDataObj.setCountry((String)linkedHashMap.get("country"));
			weatherDataObj.setTimeZone((String)linkedHashMap.get("timeZone"));
			System.out.println("WeatherDataobject created! Temp is: ");
			System.out.println(weatherDataObj.getTemperature());
			myWeatherDataCollection.setWeatherDataObject(weatherDataObj);
		}
		System.out.println("Weatherdatacollection is set! Size is: ");
		System.out.println(myWeatherDataCollection.getSizeOfWeatherArray());		
		
		navSelPage.setSelectedPage("Home");	
		System.out.println(navSelPage.getSelectedPage());
		model.addAttribute("navSelPage",navSelPage);
		return "home";
	}
	
	
	
	
	/*--------------------------------------------FUNCTION TO HANDLE SELECTED CITY---------------------------------------------*/
	@RequestMapping(value="/selected", method=RequestMethod.POST)	
	public String getSelectedWeather(@Valid @ModelAttribute("weatherLocationShort") WeatherLocShort weatherLocShort, BindingResult bindingResult,Model model){
		System.out.println("AT GETSELECTEDWEATHER!");
		if(bindingResult.hasErrors()){
			System.out.println("ERROR ON VALIDATING WEATHERLOCSHORT");
			return "home";
		}
		System.out.println("VALIDATING WEATHERLOCSHORT WORKED");
		System.out.println("CITY is: " + weatherLocShort.getCityShort());
		
		if(weatherLocShort.getCityShort().equals("Trollhättan")){
			city="Trollhättan";
			district="Västra_Götaland";
			country="Sweden";
		}
		else if(weatherLocShort.getCityShort().equals("Göteborg")){
			city="Gothenburg";
			district="Västra_Götaland";
			country="Sweden";
		}
		else if(weatherLocShort.getCityShort().equals("Stockholm")){
			city="Stockholm";
			district="Stockholm";
			country="Sweden";
		}
		else if(weatherLocShort.getCityShort().equals("Umeå")){
			city="Umeå";
			district="Västerbotten";
			country="Sweden";
		}		
		else if(weatherLocShort.getCityShort().equals("New York")){
			city="New_York";
			district="New_York";
			country="United_States";
		}
		else if(weatherLocShort.getCityShort().equals("Paris")){
			city="Paris";
			district="Île-de-France";
			country="France";
		}
		else if(weatherLocShort.getCityShort().equals("Melbourne")){
			city="Melbourne";
			district="Victoria";
			country="Australia";
		}
		else if(weatherLocShort.getCityShort().equals("Tokyo")){
			city="Tokyo";
			district="Tokyo";
			country="Japan";
		}
		
		@SuppressWarnings("rawtypes")
		List<LinkedHashMap> weatherDataMapList = getWeatherDataFromDefCity(country,district,city);
		System.out.println("Size of WeatherData: " + weatherDataMapList.size());		
		System.out.println(weatherDataMapList);
		
		//Clear WeatherDataCollection before filling it!
		myWeatherDataCollection.clearWeatherDataCollList();
		
		//setWeatherObjPutInCollection();
		for (@SuppressWarnings("rawtypes") LinkedHashMap linkedHashMap : weatherDataMapList) {
			WeatherData weatherDataObj = new WeatherData();
			weatherDataObj.setTimeFrom((String)linkedHashMap.get("timeFrom"));
			weatherDataObj.setTimeTo((String)linkedHashMap.get("timeTo"));
			weatherDataObj.setWeatherDescription((String)linkedHashMap.get("weatherDescription"));
			weatherDataObj.setPrecipitation((String)linkedHashMap.get("precipitation"));
			weatherDataObj.setWindDirection((String)linkedHashMap.get("windDirection"));
			weatherDataObj.setWindSpeed((String)linkedHashMap.get("windSpeed"));
			weatherDataObj.setWindSpeedDescription((String)linkedHashMap.get("windSpeedDescription"));
			weatherDataObj.setTemperature((String)linkedHashMap.get("temperature"));
			weatherDataObj.setPressureUnit((String)linkedHashMap.get("pressureUnit"));
			weatherDataObj.setPressureValue((String)linkedHashMap.get("pressureValue"));
			weatherDataObj.setCity((String)linkedHashMap.get("city"));
			weatherDataObj.setCountry((String)linkedHashMap.get("country"));
			weatherDataObj.setTimeZone((String)linkedHashMap.get("timeZone"));
			System.out.println("WeatherDataobject created! Temp is: ");
			System.out.println(weatherDataObj.getTemperature());
			myWeatherDataCollection.setWeatherDataObject(weatherDataObj);
		}
		System.out.println("Weatherdatacollection is set! Size is: ");
		System.out.println(myWeatherDataCollection.getSizeOfWeatherArray());
		
		navSelPage.setSelectedPage("Home");
		System.out.println(navSelPage.getSelectedPage());
		
		//Set active page for css
		navSelPage.setSelectedPage("Home");	
		System.out.println(navSelPage.getSelectedPage());
		model.addAttribute("navSelPage",navSelPage);
		return "home";
	}
	
	
	
	/*--------------------------------------------FUNCTION TO HANDLE LOGIN PAGE-----------------------------------------------*/
	@RequestMapping(value="/login", method=RequestMethod.GET)	
	public String getLoginPage(){		
		return "login";
	}
	
	
	
	
	
	
	/*------------------------------------------------MODELATTRIBUTES----------------------------------------------------------*/
	@ModelAttribute("weatherData")
	private ArrayList<WeatherData> getMyWeatherDataCollection(){
		return myWeatherDataCollection.getWeatherDataCollList();
	}	
	@ModelAttribute("weatherLocation")
	private WeatherLocation getWeatherLocation(){
		return new WeatherLocation();
	}
	@ModelAttribute("weatherLocationShort")
	private WeatherLocShort getWeatherLocShort(){
		return new WeatherLocShort();
	}	
	
	
	
	
	
	/*-------------------------------------------FUNCTIONS---------------------------------------------------------------------*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<LinkedHashMap> getWeatherDataFromDefCity(String country, String district, String city){
		restTemplate=new RestTemplate();
		//link = "http://localhost:8081/SimpleWebService/weather?country=Sweden&district=Västra_Götaland&city=Vänersborg";
		try {
			//linkUri = new URI("http://localhost:8081/SimpleWebService/weather?country=Sweden&district=Västra_Götaland&city=Vänersborg");
			//linkUri = new URI("http://localhost:8081/SimpleWebService/weather?country=" + country  + "&district=" + district + "&city=" + city + "");
			linkUri = new URI("http://localhost/SimpleWebService/weather?country=" + country  + "&district=" + district + "&city=" + city + "");
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		
		//List<LinkedHashMap> weatherData = restTemplate.getForObject("http://localhost:8081/SimpleWebService/weather?country=Sweden&district=Västra_Götaland&city=Vänersborg", List.class);
		return restTemplate.getForObject(linkUri, List.class);
	}	
	public void setMyWeatherDataCollection(
			WeatherDataCollection myWeatherDataCollection) {
		this.myWeatherDataCollection = myWeatherDataCollection;
	}
	
	
	
}