package com.sm.sendmail.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("sendgrid")
public class SendGridConfig {

	private String url;
	
	public String getApiKey() {
		String apiKey = System.getenv("SENDGRID_APIKEY");
		if(apiKey == null) {
			// try to check for system properties for the api key
			apiKey = System.getProperty("sendgrid.apiKey");
		}
		if(apiKey == null || apiKey.trim().isEmpty()) {
			System.out.println("API Key for SendGrid is not configured properly!!");
		}
		return apiKey;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
}
