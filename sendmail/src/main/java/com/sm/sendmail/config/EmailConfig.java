package com.sm.sendmail.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("email")
public class EmailConfig {
	
	private String from = "from";
	
	
	public String getFrom() {
		return from;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
}
