package com.dev.spring.util;

import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import com.dev.spring.model.User;

/** 
* This class provides implementation for the methods of Email client.
* @author  Sandeep Shetty
* @version 1.0
* @since   2017-09-09
*/
@Component
public class EmailClient {
	

	private static final Logger logger = LoggerFactory.getLogger(EmailClient.class);
	private static final String SMTP_MAIL_SERVER = "smtp.gmail.com";
	private static final int SMTP_MAIL_SERVER_PORT = 587;
	private static final String YOUR_MAIL_ID = "Your-gmail-id";
	private static final String YOUR_MAIL_PASSWORD = "Your-gmail-password";
	
	private static JavaMailSenderImpl mailSender = null;
   
	static {
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.starttls.enable", "true");
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.transport.protocol", "smtp");
		javaMailProperties.put("mail.debug", "true");
		
		mailSender = new JavaMailSenderImpl();
		mailSender.setHost(SMTP_MAIL_SERVER);
		mailSender.setPort(SMTP_MAIL_SERVER_PORT);
		mailSender.setUsername(YOUR_MAIL_ID);
		mailSender.setPassword(YOUR_MAIL_PASSWORD);
		mailSender.setJavaMailProperties(javaMailProperties);
	}
	
	/**
	 * This method sends email
	 * @param sessionTokenId
	 */
	public void send(final String sessionTokenId, final User user) {
		try {
			MimeMessagePreparator preparator = getMessagePreparator(sessionTokenId, user);
			mailSender.send(preparator);
		} catch (MailException ex) {
			logger.error(ex.getMessage(), ex);
		}
	}
	
    /**
	 * This method prepares email message 
	 * @param sessionTokenId
	 * @param user
	 * @return MimeMessagePreparator
	 * @throws Exception
	 */
    public MimeMessagePreparator getMessagePreparator(final String sessionTokenId, final User user) {		 
        MimeMessagePreparator preparator = new MimeMessagePreparator() { 
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setFrom(YOUR_MAIL_ID);
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));                
                String urlTemplate = MessageFormat.format(UserRestURIConstants.APPLICATION_LOGIN_LINK, sessionTokenId);                
                mimeMessage.setText("Dear " + user.getName() + ", your login link is " + urlTemplate + ".");
                mimeMessage.setSubject("Your login link");
            }
        };
        return preparator;
    }
    


}
