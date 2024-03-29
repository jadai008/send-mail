package com.sm.sendmail;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sm.sendmail.config.EmailConfig;
import com.sm.sendmail.model.EmailData;
import com.sm.sendmail.service.impl.AbstractEmailService;

public class DummyService extends AbstractEmailService {

	private Logger logger = LoggerFactory.getLogger(DummyService.class);

	private static final String TIMEOUT_URL = "http://localhost:8080/sendTimeOut";

	public DummyService(EmailConfig config) {
		super(config);
	}

	@Override
	public HttpPost buildRequest(EmailData data) throws Exception {
		HttpPost request = new HttpPost();
		request.setURI(new URI(TIMEOUT_URL));
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(Constants.TIMEOUT, String.valueOf(getEmailConfig().getTimeout())));
		try {
			request.setEntity(new UrlEncodedFormEntity(params));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		return request;
	}

	@Override
	public String getProviderName() {
		return "Dummy Service";
	}

}
