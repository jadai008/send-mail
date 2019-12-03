package com.sm.sendmail.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration specific to Mailgun
 * @author jadai008
 *
 */
@ConfigurationProperties("mailgun")
public class MailGunConfig {
	
	private String urlPfx;
	
	private String urlSfx;
	
	public String getUrlPfx() {
		return urlPfx;
	}
	
	public void setUrlPfx(String urlPfx) {
		this.urlPfx = urlPfx;
	}
	
	public String getUrlSfx() {
		return urlSfx;
	}
	
	public void setUrlSfx(String urlSfx) {
		this.urlSfx = urlSfx;
	}
	
	public String getUrl() {
		String domain = System.getenv("MAILGUN_DOMAIN");
		if(domain == null) {
			// try to check for system properties for the api key
			domain = System.getProperty("mailgun.domain");
		}
		if(domain == null || domain.trim().isEmpty()) {
			System.out.println("Domain for Mailgun is not configured properly!!");
			return "";
		}
		String url = urlPfx + domain + urlSfx;
		System.out.println("Mailgun url = " + url);
		return url;
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
