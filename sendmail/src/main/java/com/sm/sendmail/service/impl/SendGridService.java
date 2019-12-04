package com.sm.sendmail.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sm.sendmail.Constants;
import com.sm.sendmail.config.EmailConfig;
import com.sm.sendmail.config.SendGridConfig;
import com.sm.sendmail.model.EmailData;

@Service
@Qualifier("primary")
public class SendGridService extends  AbstractEmailService {
	
	private SendGridConfig sgConfig;
	
	public SendGridService(EmailConfig emailConfig, SendGridConfig sgConfig) {
		super(emailConfig);
		this.sgConfig = sgConfig;
	}

	@Override
	public HttpResponse sendEmail(EmailData emailData) {
		HttpResponse response =  super.sendEmail(emailData);
		if(response.getStatusLine().getStatusCode() == HttpStatus.ACCEPTED.value()) {
			return new BasicHttpResponse(new HttpVersion(1, 1), HttpStatus.OK.value(), "Email sent successfully!");
		}
		return response;
	}

	@Override
	public HttpPost buildRequest(EmailData data) throws Exception {
		HttpPost request = new HttpPost();
		request.setURI(new URI(sgConfig.getUrl()));
		request.addHeader("Authorization", "Bearer " + sgConfig.getApiKey());
		request.addHeader("Content-Type", "application/json");

		try {
			StringEntity entity = new StringEntity(toJson(data));
			request.setEntity(entity);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return request;
	}

	@Override
	public String getProviderName() {
		return "SendGrid";
	}

	
	private String toJson(EmailData data) {
		StringBuffer json = new StringBuffer();
		json.append('{');
		String toList = getRecipientList(data.getTo(), Constants.TO);
		String ccList = getRecipientList(data.getCc(), Constants.CC);
		String bccList = getRecipientList(data.getBcc(), Constants.BCC);
		json.append("\"personalizations\" : [").append(toList);
		if(!toList.isEmpty() && (!ccList.isEmpty() || !bccList.isEmpty())) {
			json.append(',');
		}
		json.append(ccList);
		if(!ccList.isEmpty() && !bccList.isEmpty()) {
			json.append(',');
		}
		json.append(bccList);
		json.append("],").append("\"from\" : ").append("{\"email\" : \"").append(getEmailConfig().getFrom());
		json.append("\"}").append(",");
		json.append("\"subject\" : ").append("\"").append(data.getSubject()).append("\",");
		json.append("\"content\" : [{\"type\" : \"text/plain\", \"value\" : \"").append(data.getBody()).append("\"}");
		json.append("]}");
		return json.toString();
	}

	private String getRecipientList(String[] recipients, String recipientType) {
		if(recipients == null || recipients.length == 0) {
			return "";
		}
		StringBuffer list = new StringBuffer("{\"").append(recipientType).append("\" : [");
		for(int i = 0; i < recipients.length; i++) {
			list.append("{\"email\" : \"").append(recipients[i]).append("\"}");
			if(i < recipients.length - 1) {
				list.append(',');
			}
		}
		list.append("]}");
		return list.toString();
	}

}
