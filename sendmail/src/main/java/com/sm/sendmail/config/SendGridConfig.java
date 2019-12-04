package com.sm.sendmail.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.sm.sendmail.Constants;

/**
 * Configuration specific to SendGrid
 * 
 * @author jadai008
 *
 */
@ConfigurationProperties("sendgrid")
public class SendGridConfig {

	private Logger logger = LoggerFactory.getLogger(SendGridConfig.class);

	private String url;

	public String getApiKey() {
		String apiKey = System.getenv(Constants.SENDGRID_API_KEY);
		if (apiKey == null) {
			// try to check for system properties for the api key
			apiKey = System.getProperty(Constants.SENDGRID_API_KEY_PROP);
		}
		if (apiKey == null || apiKey.trim().isEmpty()) {
			logger.error("API Key for SendGrid is not configured properly!!");
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
