package org.myWeather.web.domain;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
//@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Navigation implements Serializable {
	
	private static final long serialVersionUID = 3300836467177061153L;
	
	private String selectedPage;

	public String getSelectedPage() {
		return selectedPage;
	}
	public void setSelectedPage(String selectedPage) {
		this.selectedPage = selectedPage;
	}	
}
