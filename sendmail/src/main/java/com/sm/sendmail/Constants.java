package com.sm.sendmail;

/**
 * Interface to hold constants
 * 
 * @author jadai008
 *
 */
public interface Constants {

	/**
	 * <code>from</code> parameter for the email
	 */
	String FROM = "from";

	/**
	 * <code>to</code> parameter for the email
	 */
	String TO = "to";

	/**
	 * <code>subject</code> parameter for the email
	 */
	String SUBJECT = "subject";

	/**
	 * <code>text</code> parameter for the email
	 */
	String TEXT = "text";

	/**
	 * <code>bcc</code> parameter for the email
	 */
	String BCC = "bcc";

	/**
	 * <code>cc</code> parameter for the email
	 */
	String CC = "cc";

	/**
	 * <code>timeout</code> parameter to simulate time out
	 */
	String TIMEOUT = "timeOut";

	/**
	 * Environment variable for MailGun domain name
	 */
	String MAILGUN_DOMAIN = "MAILGUN_DOMAIN";

	/**
	 * System properties key for MailGun domain name
	 */
	String MAILGUN_DOMAIN_PROP = "mailgun.domain";

	/**
	 * Environment variable for MailGun API key
	 */
	String MAILGUN_API_KEY = "MAILGUN_APIKEY";

	/**
	 * System properties key for MailGun API key
	 */
	String MAILGUN_API_KEY_PROP = "mailgun.apiKey";

	/**
	 * Environment variable for SendGrid API key
	 */
	String SENDGRID_API_KEY = "SENDGRID_APIKEY";

	/**
	 * System properties key for SendGrid API key
	 */
	String SENDGRID_API_KEY_PROP = "sendgrid.apiKey";

}
