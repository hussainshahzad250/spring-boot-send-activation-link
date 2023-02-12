package com.sas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.File;


public interface EmailService {

    public void sendEmail(SimpleMailMessage email);

    public void sendEmail(String subject, String[] to, String[] cc, String html) throws MessagingException;

    void sendEmailWithAttachment(File file, String body, String subject, String to, String[] cc, String fileName);

}
