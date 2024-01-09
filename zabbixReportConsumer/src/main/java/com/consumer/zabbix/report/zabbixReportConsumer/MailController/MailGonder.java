package com.consumer.zabbix.report.zabbixReportConsumer.MailController;

    import javax.mail.*;
    import javax.mail.internet.InternetAddress;
    import javax.mail.internet.MimeBodyPart;
    import javax.mail.internet.MimeMessage;
    import javax.mail.internet.MimeMultipart;
    import java.util.Properties;

public class MailGonder {

        public static void main(String[] args) throws MessagingException {
            // Gönderici ve alıcı bilgilerini belirtin.
            String senderEmail = "kaan.boldan@gizdanismanlik.com.tr";
            String senderPassword = "nyhqy7-Kowtyb-hykvor";
            String recipientEmail = "kaanboldan@gmail.com";

            // Mailin içeriğini belirtin.
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setText("Merhaba,\n\nBu bir test mailidir.\n\nSaygılarımla,\n[Gönderen]");

            // `Authenticator` nesnesini oluşturun.
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.office365.com");  // Mail sunucunuzun adresini girin
            props.put("mail.smtp.port", "587");  // Mail sunucunuzun portunu girin
            props.put("mail.smtp.auth", "true");  // SMTP kimlik doğrulamasını etkinleştirin
            props.put("mail.smtp.ssl.enable", "tls");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.starttls.enable", "true");





            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
            };
            // `Session` nesnesini oluşturun.

            Session session = Session.getInstance(props, authenticator);
            session.setDebug(true);


            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            bodyPart = new MimeBodyPart();
            bodyPart.setText("Merhaba,\n\nBu bir test mailidir.\n\nSaygılarımla,\n[Gönderen]");
// Multipart nesnesini oluşturun.
            Multipart multipart = new MimeMultipart();
            // BodyPart nesnesini multipart'a ekleyin.
            multipart.addBodyPart(bodyPart);
// Multipart nesnesini Message'a atayın.
            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Mail başarıyla gönderildi!");
        }
    }

