package com.sm.sendmail.service.impl;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHttpResponse;
import org.springframework.http.HttpStatus;

import com.sm.sendmail.config.EmailConfig;
import com.sm.sendmail.model.EmailData;
import com.sm.sendmail.service.EmailService;

/**
 * Abstraction layer before the implementations. Generic parameters are used
 * here and common methods like sending email and using the timeout are
 * implemented here
 * 
 * @author jadai008
 *
 */
public abstract class AbstractEmailService implements EmailService {

	private EmailConfig emailConfig;

	public AbstractEmailService(EmailConfig config) {
		this.emailConfig = config;
	}

	@Override
	public HttpResponse sendEmail(EmailData emailData) {
		HttpClient httpCLient = HttpClientBuilder.create().setDefaultRequestConfig(getRequestConfig()).build();
		String errMsg = "";
		try {
			HttpPost request = buildRequest(emailData);
			HttpResponse response = httpCLient.execute(request);
			System.out.println(response);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			errMsg = e.getMessage();
		}

		return new BasicHttpResponse(new HttpVersion(1, 1), HttpStatus.INTERNAL_SERVER_ERROR.value(), errMsg);

	}

	/**
	 * Method to create the default {@link RequestConfig} instance for the http
	 * client. Sub classes can override to set provider specific configurations if
	 * any.
	 * 
	 * @return
	 */
	protected RequestConfig getRequestConfig() {
		int timeout = emailConfig.getTimeout() * 1000;
		System.out.println("Time out is configured for " + emailConfig.getTimeout() + " seconds.");
		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout).setSocketTimeout(timeout)
				.setConnectTimeout(timeout).build();
		return config;
	}

	public EmailConfig getEmailConfig() {
		return emailConfig;
	}

}
