package com.sm.sendmail.controllers;

import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sm.sendmail.model.EmailData;
import com.sm.sendmail.service.EmailService;
import com.sm.sendmail.util.EmailDataValidator;

@RestController
public class MailController {

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
		System.out.println("Trying to send through email provider: " + primaryService.getProviderName());
		HttpResponse response = primaryService.sendEmail(content);
		if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
			return successEntity;
		} else {
			appendProviderBasedError(messages, primaryService.getProviderName(), response);
			System.out.println("Failed to send using primary provider. Trying to send through secondary provider: "
					+ secondaryService.getProviderName());
			response = secondaryService.sendEmail(content);
			if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
				return successEntity;
			} else {
				appendProviderBasedError(messages, secondaryService.getProviderName(), response);
			}
		}
		return new ResponseEntity<String>(
				"Could not send email through any configured service = " + messages.toString(),
				HttpStatus.INTERNAL_SERVER_ERROR);
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
			e.printStackTrace();
		}
		return "Success!!";
	}

}