package com.sm.sendmail.service;

import org.apache.http.HttpResponse;

import com.sm.sendmail.model.EmailData;

/**
 * Service interface for email related activities
 * 
 * @author jadai008
 *
 */
public interface EmailService {

	/**
	 * Sends email using one of the configured providers. The necessary details like
	 * from, to, subject and message are passed through {@link EmailData} instance
	 * 
	 * @param emailData
	 * @return the response after the attempt to send email
	 */
	public HttpResponse sendEmail(EmailData emailData);
	
	/**
	 * Returns the name of this service provider.
	 * @return
	 */
	public String getProviderName();

}
