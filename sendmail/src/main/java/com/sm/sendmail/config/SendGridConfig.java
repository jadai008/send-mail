package com.sm.sendmail.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("sendgrid")
public class SendGridConfig {

	private String apiKey;
	
	private String url;
	
	public String getApiKey() {
		return apiKey;
	}
	
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
}
