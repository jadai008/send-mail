package com.sm.sendmail;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.apache.http.HttpResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sm.sendmail.config.EmailConfig;
import com.sm.sendmail.config.MailGunConfig;
import com.sm.sendmail.config.SendGridConfig;
import com.sm.sendmail.model.EmailData;
import com.sm.sendmail.service.EmailService;

@SpringBootTest
class SendMailApplicationTests {

	@Autowired
	private List<EmailService> emailServices;

	@Autowired
	private EmailConfig eConfig;

	@Autowired
	private MailGunConfig mConfig;

	@Autowired
	private SendGridConfig sConfig;

	private static EmailData okData;

	private static EmailData errorData;

	@BeforeAll
	public static void initData() {
		initOkData();
		initErrorData();
	}

	private static void initErrorData() {
		errorData = new EmailData();
	}

	private static void initOkData() {
		okData = new EmailData();
		okData.setTo(new String[] { "jadai008@gmail.com" });
		okData.setSubject("Hello");
		okData.setBody("Simple text message");
	}

	@Test
	void testProvidersSetup() {
		assertThat(emailServices).isNotNull();
		assertThat(emailServices).isNotEmpty();
		assertThat(emailServices.size()).isEqualTo(2); // including dummy service
	}

	@Test
	void mailGunSendOk() {
		HttpResponse resp = TestUtils.sendMail(TestUtils.getMailGunService(eConfig, mConfig), okData);
		assertThat(resp.getStatusLine().getStatusCode()).isEqualTo(200);
	}

	@Test
	void sendGridSendOk() {
		HttpResponse resp = TestUtils.sendMail(TestUtils.getSendGridService(eConfig, sConfig), okData);
		assertThat(resp.getStatusLine().getStatusCode()).isEqualTo(200);
	}

	@Test
	void mailGunSendError() {
		HttpResponse resp = TestUtils.sendMail(TestUtils.getMailGunService(eConfig, mConfig), errorData);
		assertThat(resp.getStatusLine().getStatusCode()).isNotEqualTo(200);
	}

	@Test
	void sendGridSendError() {
		HttpResponse resp = TestUtils.sendMail(TestUtils.getSendGridService(eConfig, sConfig), errorData);
		assertThat(resp.getStatusLine().getStatusCode()).isNotEqualTo(200);
	}

	/**
	 * This is to test timeouts in real services. The main application should be
	 * running while this test is being executed because the {@link DummyService}
	 * uses the url <code>http://localhost:port/sendTimeOut</code> api to simulate
	 * time out
	 */
	@Test
	void testTimeOut() {
		HttpResponse resp = TestUtils.sendMail(new DummyService(eConfig), okData);
		assertThat(resp.getStatusLine().getStatusCode()).isEqualTo(500);
		assertThat(resp.getStatusLine().getReasonPhrase()).isEqualTo("Read timed out");
	}

}
