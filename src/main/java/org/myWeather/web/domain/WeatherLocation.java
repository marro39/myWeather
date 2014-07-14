package org.myWeather.web.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

public class WeatherLocation implements Serializable{
	private static final long serialVersionUID = 1005717887925358781L;
	
	@NotNull
	@NotEmpty
	private String country;
	
	@NotNull
	@NotEmpty
	private String district;
	
	@NotNull
	@NotEmpty
	private String city;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}	
}
