package com.sm.sendmail.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sm.sendmail.Constants;
import com.sm.sendmail.config.EmailConfig;
import com.sm.sendmail.config.MailGunConfig;
import com.sm.sendmail.model.EmailData;

@Service
@Qualifier("secondary")
public class MailGunService  extends  AbstractEmailService  {
	
	private MailGunConfig mgConfig;
	
	public MailGunService(EmailConfig eConfig, MailGunConfig mConfig) {
		super(eConfig);
		this.mgConfig = mConfig;
	}

	public HttpPost buildRequest(EmailData data) throws URISyntaxException {
		HttpPost request = new HttpPost();
		request.setURI(new URI(mgConfig.getUrl()));
		request.addHeader("Authorization", "Basic " + Base64.encodeBase64String(mgConfig.getApiKey().getBytes()));
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(Constants.FROM, getEmailConfig().getFrom()));
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
