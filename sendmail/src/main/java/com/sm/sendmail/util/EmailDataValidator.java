package com.sm.sendmail.util;

import java.util.regex.Pattern;

import com.sm.sendmail.model.EmailData;

/**
 * Utility class to validate the input on the backend.
 * 
 * @author jadai008
 *
 */
public class EmailDataValidator {

	// compile a pattern with simple email matching regex
	private static final String EMAIL_PATTERN = "\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})";
	private static final int MAX_SIZE = 25 * 1024 * 1024; // Mailgun limit

	/**
	 * Validates and returns the first validation error message. Validation stops
	 * after the first error. If there are no validation errors in the given input
	 * then <code>null</code> is returned.
	 * 
	 * @param data
	 * @return the first validation error message or null if there are no validation
	 *         errors
	 */
	public static String validate(EmailData data) {
		String errMsg = validateMandatoryFields(data);
		if (errMsg != null) {
			return errMsg;
		}
		errMsg = validateRecipients(data);
		if (errMsg != null) {
			return errMsg;
		}
		errMsg = validateSize(data);
		return errMsg;
	}

	private static String validateMandatoryFields(EmailData data) {
		if (data == null) {
			return "No email data provided";
		}
		if (data.getTo() == null || data.getTo().length == 0) {
			return "At least one recipient should be specified in 'To'";
		}
		if (data.getBody() == null || data.getBody().isEmpty()) {
			return "Email message is empty";
		}
		if (data.getSubject() == null || data.getSubject().isEmpty()) {
			return "Subject is empty";
		}
		return null;
	}

	private static String validateSize(EmailData data) {
		String body = data.getBody();
		if (body != null) {
			if (body.getBytes().length > MAX_SIZE) {
				return "The size of the email is beyond the allowed limit (25MB).";
			}
		}
		return null;
	}

	private static String validateRecipients(EmailData data) {
		String errMsg = validateEmailFormats(data.getTo());
		if (errMsg != null) {
			return errMsg;
		}
		errMsg = validateEmailFormats(data.getCc());
		if (errMsg != null) {
			return errMsg;
		}
		errMsg = validateEmailFormats(data.getBcc());
		if (errMsg != null) {
			return errMsg;
		}
		int total = (data.getTo() != null ? data.getTo().length : 0) + (data.getCc() != null ? data.getCc().length : 0)
				+ (data.getBcc() != null ? data.getBcc().length : 0);
		if (total >= 1000) { // sendgrid limit
			return "Number of recipients exceeded the allowed limit (999).";
		}
		return null;
	}

	private static String validateEmailFormats(String[] emails) {
		if (emails == null || emails.length == 0) {
			return null;
		}
		for (String email : emails) {
			if (!Pattern.matches(EMAIL_PATTERN, email)) {
				return "'" + email + "' is not a valid email address";
			}
		}
		return null;
	}

}
