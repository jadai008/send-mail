package com.sm.sendmail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.sm.sendmail.config.EmailConfig;
import com.sm.sendmail.config.MailGunConfig;

@SpringBootApplication
@EnableConfigurationProperties({EmailConfig.class, MailGunConfig.class})
public class SendMailApplication {

	public static void main(String[] args) {
		SpringApplication.run(SendMailApplication.class, args);
	}

}
