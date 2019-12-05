package com.sm.sendmail.controllers;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sm.sendmail.model.EmailData;
import com.sm.sendmail.service.EmailService;
import com.sm.sendmail.util.EmailDataValidator;

@CrossOrigin("*")
@RestController
public class MailController {

	private Logger logger = LoggerFactory.getLogger(MailController.class);

	@Autowired
	@Qualifier("primary")
	private EmailService primaryService;

	@Autowired
	@Qualifier("secondary")
	private EmailService secondaryService;

	@PostMapping("/send")
	public ResponseEntity<String> sendMail(@RequestBody EmailData content) {
		ResponseEntity<String> successEntity = new ResponseEntity<String>("Email successfully sent!!", HttpStatus.OK);
		String errMsg = EmailDataValidator.validate(content);
		if (errMsg != null) {
			return new ResponseEntity<String>(errMsg, HttpStatus.BAD_REQUEST);
		}
		StringBuffer messages = new StringBuffer();
		if (tryService(primaryService, content, messages) || tryService(secondaryService, content, messages)) {
			return successEntity;
		} else {
			return new ResponseEntity<String>(
					"Could not send email through any configured service = " + messages.toString(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private boolean tryService(EmailService service, EmailData data, StringBuffer msgs) {
		String providerName = service.getProviderName();
		logger.info("Trying to send through email provider: " + providerName);
		HttpResponse response = service.sendEmail(data);
		if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
			logger.info("**** Email dispatched successfully through provider: " + providerName + " ****");
			return true;
		} else {
			appendProviderBasedError(msgs, providerName, response);
			logger.warn("Failed to send using provider " + providerName + " : "
					+ response.getStatusLine().getReasonPhrase());
		}
		return false;
	}

	private void appendProviderBasedError(StringBuffer messages, String providerName, HttpResponse response) {
		messages.append("Could not send email through provider ").append(providerName);
		messages.append(": ").append(response.getStatusLine().getReasonPhrase());
		messages.append(' ').append(String.valueOf(response.getStatusLine().getStatusCode()));
		messages.append('\n');
	}

	/**
	 * This method is just to simulate time out. This just sleeps for the
	 * <code>timeout</code> + 5 seconds so that the calling http client times out.
	 * The <code>timeout</code> should be passed by the calling client to match its
	 * time out limits
	 * 
	 * @param timeOut
	 * @return
	 */
	@PostMapping("/sendTimeOut")
	public String sendTimeOut(@RequestParam int timeOut) {
		try {
			Thread.sleep((timeOut + 5) * 1000);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
		return "Success!!";
	}

}