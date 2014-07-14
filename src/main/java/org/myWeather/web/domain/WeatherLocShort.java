package org.myWeather.web.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class WeatherLocShort implements Serializable {	
	
	private static final long serialVersionUID = -4783339104928705308L;
	
	@NotNull
	@NotEmpty
	private String cityShort;

	public String getCityShort() {
		return cityShort;
	}
	public void setCityShort(String cityShort) {
		this.cityShort = cityShort;
	}	
}
