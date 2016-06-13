package vvsvintsitsky.testing.webapp.common.mailsender;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

	private String from;
	private String to;
	private String userId;
	private String password;
	private String messageSubject;
	private String messageText;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMessageSubject() {
		return messageSubject;
	}

	public void setMessageSubject(String messageSubject) {
		this.messageSubject = messageSubject;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public void sendMail() {

		// Get system properties
		Properties properties = System.getProperties();

		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");

		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		Authenticator authenticator = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userId, password);// userid
																	// and
																	// password
																	// for
																	// "from"
																	// email
																	// address
			}
		};

		Session session = Session.getDefaultInstance(properties, authenticator);
		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject(messageSubject);

			// Now set the actual message
			message.setText(messageText);

			// Send message
			Transport.send(message);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
