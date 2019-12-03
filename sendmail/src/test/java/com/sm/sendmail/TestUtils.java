package com.sm.sendmail;

import org.apache.http.HttpResponse;

import com.sm.sendmail.config.EmailConfig;
import com.sm.sendmail.config.MailGunConfig;
import com.sm.sendmail.config.SendGridConfig;
import com.sm.sendmail.model.EmailData;
import com.sm.sendmail.service.EmailService;
import com.sm.sendmail.service.impl.MailGunService;
import com.sm.sendmail.service.impl.SendGridService;

/**
 * Utility class for some test helper methods.
 * 
 * @author jadai008
 *
 */
public class TestUtils {

	private static MailGunService mgService;

	private static SendGridService sgService;

	/**
	 * This utility method is for getting an instance of {@link MailGunService}. If
	 * the instance is already available, that is returned, else a new instance is
	 * created.
	 * 
	 * @param eConfig
	 * @param mConfig
	 * @return
	 */
	public static MailGunService getMailGunService(EmailConfig eConfig, MailGunConfig mConfig) {
		if (mgService == null) {
			mgService = new MailGunService(eConfig, mConfig);
		}
		return mgService;
	}

	/**
	 * This utility method is for getting an instance of {@link SendGridService}. If
	 * the instance is already available, that is returned, else a new instance is
	 * created.
	 * 
	 * @param eConfig
	 * @param sConfig
	 * @return
	 */
	public static SendGridService getSendGridService(EmailConfig eConfig, SendGridConfig sConfig) {
		if (sgService == null) {
			sgService = new SendGridService(eConfig, sConfig);
		}
		return sgService;
	}

	/**
	 * Utility method to send email using a service provider
	 * 
	 * @param service
	 * @param data
	 * @return the response after executing the request.
	 */
	public static HttpResponse sendMail(EmailService service, EmailData data) {
		return service.sendEmail(data);
	}

}
