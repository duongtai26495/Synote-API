package com.duongtai.syndiary.configs;

import org.springframework.boot.autoconfigure.web.ServerProperties;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmail {
    public static void sendForgotPassword(String mail_to, String sub, String msg, final String email_master, final String password_master){
        Properties properties = new Properties();

        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");


        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email_master, password_master);
            }
        });

        try
        {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email_master));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail_to));
            message.setSubject(sub);
            message.setContent(msg,"text/html");

            Transport.send(message);
        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
