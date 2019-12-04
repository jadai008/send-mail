package com.sm.sendmail.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.sm.sendmail.Constants;

/**
 * Configuration specific to MailGun
 * 
 * @author jadai008
 *
 */
@ConfigurationProperties("mailgun")
public class MailGunConfig {

	private Logger logger = LoggerFactory.getLogger(MailGunConfig.class);

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
		String domain = System.getenv(Constants.MAILGUN_DOMAIN);
		if (domain == null) {
			// try to check for system properties for the api key
			domain = System.getProperty(Constants.MAILGUN_DOMAIN_PROP);
		}
		if (domain == null || domain.trim().isEmpty()) {
			logger.error("Domain for Mailgun is not configured properly!!");
			return "";
		}
		String url = urlPfx + domain + urlSfx;
		logger.info("Mailgun url = " + url);
		return url;
	}

	public String getApiKey() {
		String apiKey = System.getenv(Constants.MAILGUN_API_KEY);
		if (apiKey == null) {
			// try to check for system properties for the api key
			apiKey = System.getProperty(Constants.MAILGUN_API_KEY_PROP);
		}
		if (apiKey == null || apiKey.trim().isEmpty()) {
			logger.error("API Key for Mailgun is not configured properly!!");
		}
		return apiKey;
	}

}
