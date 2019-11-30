package com.sm.sendmail.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

   @GetMapping("/send")
   public ResponseEntity<String> sendMail() {
	   return new ResponseEntity<String>("Success!!", HttpStatus.OK);
   }

}