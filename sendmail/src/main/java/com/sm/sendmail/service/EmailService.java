package com.sm.sendmail.service;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHttpResponse;
import org.springframework.http.HttpStatus;

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
	public default HttpResponse sendEmail(EmailData emailData) {

		
		HttpClient httpCLient = HttpClientBuilder.create().build();
		String errMsg = "";
		try {
			HttpPost request = buildRequest(emailData);
			HttpResponse response = httpCLient.execute(request);
			System.out.println(response);
			return response;
		} catch ( Exception e) {
			e.printStackTrace();
			errMsg = e.getMessage();
		} 
		
		return new BasicHttpResponse(new HttpVersion(1, 1), HttpStatus.INTERNAL_SERVER_ERROR.value(), errMsg);
	
	}
	
	/**
	 * Service provider specific way of building the POST request to be executed while sending the email.
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public HttpPost buildRequest(EmailData data) throws Exception;
	
	/**
	 * Returns the name of this service provider.
	 * @return
	 */
	public String getProviderName();

}
