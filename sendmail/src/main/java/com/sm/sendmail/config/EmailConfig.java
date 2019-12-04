package com.sm.sendmail.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Common email configurations
 * 
 * @author jadai008
 *
 */
@ConfigurationProperties("email")
public class EmailConfig {

	private String from;

	private int timeout;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
}
