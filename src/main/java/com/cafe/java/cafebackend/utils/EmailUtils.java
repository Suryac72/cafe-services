package com.cafe.java.cafebackend.utils;

import java.util.List;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailUtils {
    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to,String subject,String body,List<String> recipients){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("suryac72@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        if(recipients!=null && recipients.size() > 0)
            message.setCc(getCCArray(recipients));
        emailSender.send(message);
    }

    private String[] getCCArray(List<String> ccList){
        String[] cc = new String[ccList.size()];
            for(int index = 0;index<ccList.size();index++){
                cc[index] = ccList.get(index);
            }
            return cc;
    }

    public void forgotMail(String to,String subject,String password) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom("suryac72@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        String htmlMsg = "<p><b>Your Login details for Cafe Management System</b><br><b>Email: </b> " + to + " <br><b>Password: </b> " + password + "<br><a href=\"http://localhost:4200/\">Click here to login</a></p>";
        message.setContent(htmlMsg,"text/html");
        emailSender.send(message);
    }

}
