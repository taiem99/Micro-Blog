package vn.techmaster.blogs.service;

import javax.mail.MessagingException;

public interface MailService {
    void sendEmail(String to, String body, String topic);
    void sendEmailAttackImage(String to, String body, String topic) throws MessagingException;
    void sendHtmlEmail(String to, String topic) throws MessagingException;
    void sendTemplateEmail(String to, String topic) throws MessagingException;
}
