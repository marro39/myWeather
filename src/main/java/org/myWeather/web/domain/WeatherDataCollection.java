package org.myWeather.web.domain;

import java.io.Serializable;
import java.util.ArrayList;

import org.myWeather.web.domain.WeatherData;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class WeatherDataCollection implements Serializable{	
	
	private static final long serialVersionUID = 1920476267894978381L;
	
	private ArrayList<WeatherData> weatherDataCollList = new ArrayList<WeatherData>();
	
	public void clearWeatherDataCollList(){
		weatherDataCollList.clear();
	}	
	public ArrayList<WeatherData> getWeatherDataCollList() {
		return weatherDataCollList;
	}
	public int getSizeOfWeatherArray(){
		return weatherDataCollList.size();
	}
	public void setWeatherDataCollList(ArrayList<WeatherData> weatherDataCollList) {
		this.weatherDataCollList = weatherDataCollList;
	}
	public void setWeatherDataObject(WeatherData weatherData){
		weatherDataCollList.add(weatherData);
	}
	
	
	
	
}
