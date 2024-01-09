package com.consumer.zabbix.report.zabbixReportConsumer.MailController;

import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class GmailSender {


    public static void sendEmail(String recipientEmail, String subject, String content, String attachmentPath, String s) {
        String senderEmail = "kaanboldan@gmail.com";
        String senderPassword = "zpka mtdh zhqu exwx"; // Replace with your actual Gmail password
System.out.println("test1");
        // Mail server settings
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.enable", "tls");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // Authenticator object
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        };
        System.out.println("test2");

        // Create a session
        Session session = Session.getInstance(props, authenticator);
        System.out.println("test3");

        try {
            System.out.println("test4");

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
            System.out.println("test5");

            // Attach the text part
            multipart.addBodyPart(textPart);

            // Attach the file part if the attachment path is provided
            if (attachmentPath != null && !attachmentPath.isEmpty()) {
                MimeBodyPart filePart = new MimeBodyPart();
                filePart.attachFile(attachmentPath);
                System.out.println("test6");

                // Set the file name (you can customize this)
                filePart.setFileName("Zabbix_report.xlsx");

                // Attach the file part
                multipart.addBodyPart(filePart);
            }
            System.out.println("test7");

            // Set the content of the message as the multipart object
            message.setContent(multipart);
            System.out.println("test8");

            // Send the email
            Transport.send(message);
            System.out.println("test9");

            System.out.println("Mail başarıyla gönderildi!");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("olmadı be : ");
            System.out.println(e.getMessage());
        }
    }
}
