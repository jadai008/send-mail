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
	
	private String apiKey;
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getApiKey() {
		return apiKey;
	}
	
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

}
