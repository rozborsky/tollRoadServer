package ua.rozborsky.tollRoadServer.classes;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.StringTerm;

/**
 * Created by roman on 30.01.2017.
 */
public class SendEmail {
    private static final Logger log = Logger.getLogger(ClientThread.class);

    private String address = Properties.address();
    private String password = Properties.passwordEmail();
    private String subject = Properties.subject();
    private String driverEmail;
    private String textMessage;

    public SendEmail(String eMail) {
        this.driverEmail = eMail;
    }

    public void send() {
        java.util.Properties props = new java.util.Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(address, SendEmail.this.password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(this.driverEmail));
            message.setSubject(subject);
            message.setText(textMessage);

            Transport.send(message);
        } catch (MessagingException e) {
            log.error(e);
        }
    }

    public void setMessage(String message) {
        this.textMessage = message;
    }
}
