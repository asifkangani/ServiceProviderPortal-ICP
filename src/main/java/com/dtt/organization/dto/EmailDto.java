package com.dtt.organization.dto;

import java.io.Serializable;
import java.util.List;

public class EmailDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private String body;
	private String emailBody;
	private List<String> recipients;
	private String subject;
	private boolean sendMailToAdmin;



	public String getEmailBody() {
		return emailBody;
	}
	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}
	public List<String> getRecipients() {
		return recipients;
	}
	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public boolean isSendMailToAdmin() {
		return sendMailToAdmin;
	}

	public void setSendMailToAdmin(boolean sendMailToAdmin) {
		this.sendMailToAdmin = sendMailToAdmin;
	}

	@Override
	public String toString() {
		return "EmailDto{" +
				"emailBody='" + emailBody + '\'' +
				", recipients=" + recipients +
				", subject='" + subject + '\'' +
				", sendMailToAdmin=" + sendMailToAdmin +
				'}';
	}
}
