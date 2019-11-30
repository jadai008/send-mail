package com.sm.sendmail.service;

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
	 * @return
	 */
	public String sendEmail(EmailData emailData);

}
