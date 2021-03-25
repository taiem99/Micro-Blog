package vn.techmaster.blogs.service;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;

public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;


    @Override
    public void sendEmail(String to, String body, String topic) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("hoaianhnotice@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(topic);
        simpleMailMessage.setText(body);
        mailSender.send(simpleMailMessage);
        
    }

    @Override
    public void sendEmailAttackImage(String to, String body, String topic) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(topic);
        helper.setText(body);

        FileSystemResource file = new FileSystemResource(new File(System.getProperty("user.dir") + "\\src\\main\\resource\\static\\") );
        helper.addAttachment(file.getFilename(), file);

        mailSender.send(message);
        
    }

    @Override
    public void sendHtmlEmail(String to, String topic) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(topic);

        String htmlMsg = "<h1>Hello</h1>";
        message.setContent(htmlMsg, "text/html");

        mailSender.send(message);
        
    }

    @Override
    public void sendTemplateEmail(String to, String topic) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(topic);

        final Context context = new Context();
        context.setVariable("name", "to");
        context.setVariable("project_name", "spring-email-with-thymeleaf Demo");


        mailSender.send(message);
        
    }
    
}
