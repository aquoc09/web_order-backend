package com.kenji.web_order.service.mail;

import com.kenji.web_order.entity.User;
import jakarta.persistence.GeneratedValue;
import lombok.AccessLevel;
import lombok.Generated;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MailServiceImp implements MailService{
    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String domainMail;

    @Override
    public String forgotPassword(User user) {
        String newPassword = UUID.randomUUID().toString();
        String domainName = "PQ Restaurant";
        String subject = "RESET PASSWORD";

        String body =
                "Hello "+ user.getFullName() +",\n" +
                        "\n" +
                        "As requested, "+ domainName + " is sending you the password information for your account on the website "+ domainName +".\n" +
                        "\n" +
                        "Password: "+ newPassword +"\n" +
                        "Thank you and have a great day.\n" +
                        domainName +"!";


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(domainMail);
        message.setTo(user.getEmail());
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);

        return newPassword;
    }
}
