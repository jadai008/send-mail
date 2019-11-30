package com.sm.sendmail.service.impl;

import org.apache.http.HttpResponse;
import org.springframework.stereotype.Service;

import com.sm.sendmail.model.EmailData;
import com.sm.sendmail.service.EmailService;

@Service
public class SendGridService implements EmailService {

	@Override
	public HttpResponse sendEmail(EmailData emailData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProviderName() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
