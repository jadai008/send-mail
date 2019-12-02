package com.sm.sendmail.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration specific to Mailgun
 * @author jadai008
 *
 */
@ConfigurationProperties("mailgun")
public class MailGunConfig {
	
	private String url;
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getApiKey() {
		String apiKey = System.getenv("MAILGUN_APIKEY");
		if(apiKey == null) {
			// try to check for system properties for the api key
			apiKey = System.getProperty("mailgun.apiKey");
		}
		if(apiKey == null || apiKey.trim().isEmpty()) {
			System.out.println("API Key for Mailgun is not configured properly!!");
		}
		return apiKey;
	}
	
}
