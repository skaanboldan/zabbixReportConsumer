package com.consumer.zabbix.report.zabbixReportConsumer.MailController;

import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class GmailSender {


    public static void sendEmail(String recipientEmail, String subject, String content, String attachmentPath, String s) {
        String senderEmail = "kaanboldan@gmail.com";
        String senderPassword = "zpka mtdh zhqu exwx"; // Replace with your actual Gmail password

        // Mail server settings
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Authenticator object
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        };

        // Create a session
        Session session = Session.getInstance(props, authenticator);

        try {
            // Create a MimeMessage
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);

            // Create multipart object for handling attachments
            MimeMultipart multipart = new MimeMultipart();

            // Create text part
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(content);

            // Attach the text part
            multipart.addBodyPart(textPart);

            // Attach the file part if the attachment path is provided
            if (attachmentPath != null && !attachmentPath.isEmpty()) {
                MimeBodyPart filePart = new MimeBodyPart();
                filePart.attachFile(attachmentPath);

                // Set the file name (you can customize this)
                filePart.setFileName("Zabbix_report.xlsx");

                // Attach the file part
                multipart.addBodyPart(filePart);
            }

            // Set the content of the message as the multipart object
            message.setContent(multipart);

            // Send the email
            Transport.send(message);

            System.out.println("Mail başarıyla gönderildi!");

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }
}
