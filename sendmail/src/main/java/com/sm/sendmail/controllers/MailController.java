package com.sm.sendmail.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sm.sendmail.service.EmailService;

@RestController
public class MailController {
	
	@Autowired
	private List<EmailService> emailServices;

	@GetMapping("/send")
	public ResponseEntity<String> sendMail(/* @RequestBody EmailData content */) {
		
		if(emailServices == null || emailServices.isEmpty()) {
			return new ResponseEntity<String>("No Email providers configured!!", HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<String>("Email providers configured = " + emailServices, HttpStatus.OK);
	}

}