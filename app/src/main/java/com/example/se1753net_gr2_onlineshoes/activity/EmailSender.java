package com.example.se1753net_gr2_onlineshoes.activity;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {
    public static boolean sendEmail(String recipient, String resetCode) {
        final String senderEmail = "quaithugaoru@gmail.com";
        final String senderPassword = "oqtt qcwz fqsx lnug";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject("Đặt lại mật khẩu");

            // Tạo URL dẫn đến trang đặt lại mật khẩu
            String resetLink = "se1753net_gr2_onlineshoes://reset-password?code=" + resetCode;

            String emailContent = "<h2>Đặt lại mật khẩu</h2>"
                    + "<p>Nhấp vào liên kết bên dưới để đặt lại mật khẩu của bạn:</p>"
                    + "<a href='" + resetLink + "' style='color: blue; text-decoration: underline;'>Đặt lại mật khẩu</a>"
                    + "<p>Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.</p>";

            message.setContent(emailContent, "text/html; charset=UTF-8");

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
