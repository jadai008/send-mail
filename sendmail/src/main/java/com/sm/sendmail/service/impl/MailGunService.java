package com.sm.sendmail.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sm.sendmail.Constants;
import com.sm.sendmail.config.EmailConfig;
import com.sm.sendmail.config.MailGunConfig;
import com.sm.sendmail.model.EmailData;
import com.sm.sendmail.service.EmailService;

@Service
public class MailGunService implements EmailService {
	
	@Autowired
	private EmailConfig emailConfig;
	
	@Autowired
	private MailGunConfig mgConfig;

	@Override
	public HttpResponse sendEmail(EmailData emailData) {
		
		HttpClient httpCLient = HttpClientBuilder.create().build();
		String errMsg = "";
		try {
			HttpPost request = buildRequest(emailData);
			HttpResponse response = httpCLient.execute(request);
			System.out.println(response);
			return response;
		} catch ( IOException | URISyntaxException e) {
			e.printStackTrace();
			errMsg = e.getMessage();
		} 
		
		return new BasicHttpResponse(new HttpVersion(1, 1), HttpStatus.INTERNAL_SERVER_ERROR.value(), errMsg);
	}
	
	private HttpPost buildRequest(EmailData data) throws URISyntaxException {
		HttpPost request = new HttpPost();
		request.setURI(new URI(mgConfig.getUrl()));
		request.addHeader("Authorization", "Basic " + Base64.encodeBase64String(mgConfig.getApiKey().getBytes()));
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(Constants.FROM, emailConfig.getFrom()));
		addRecipientList(params, data.getTo(), Constants.TO);
		addRecipientList(params, data.getCc(), Constants.CC);
		addRecipientList(params, data.getBcc(), Constants.BCC);
		params.add(new BasicNameValuePair(Constants.SUBJECT, data.getSubject()));
		params.add(new BasicNameValuePair(Constants.TEXT, data.getBody()));
		try {
			request.setEntity(new UrlEncodedFormEntity(params));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.out.println("Exception occurred: " + e);
		}
		return request;
	}

	private void addRecipientList(List<NameValuePair> params, String[] recipients, String recipientType) {
		if(recipients == null || recipients.length == 0) {
			System.out.println("No recipients provided for type " + recipientType);
			return;
		}
		for(String recipient : recipients) {
			params.add(new BasicNameValuePair(recipientType, recipient));
		}
	}
	
	@Override
	public String getProviderName() {
		return "Mailgun";
	}

}