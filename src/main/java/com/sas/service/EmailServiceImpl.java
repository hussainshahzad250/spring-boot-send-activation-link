package com.sas.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Arrays;

@Slf4j
@AllArgsConstructor
@Service("emailService")
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    private Environment environment;

//    @Autowired
//    private TemplateUtil templateUtil;

    @Async
    @Override
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
        log.info("Mail sent successfully");
    }

    public void sendEmail(String subject, String[] to, String[] cc, String html) throws MessagingException {
        if (to != null && !CollectionUtils.isEmpty(Arrays.asList(to))) {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setTo(to);
            if (cc != null && !CollectionUtils.isEmpty(Arrays.asList(cc)))
                messageHelper.setCc(cc);
            messageHelper.setSubject(subject);
            messageHelper.setText(html, true);
            messageHelper.setFrom(environment.getProperty("spring.mail.username"));
            javaMailSender.send(message);
            log.info("Mail send successfully on email {} ", Arrays.toString(to));
        } else
            log.info("No recipients found to sned mail");
    }

    @Override
    public void sendEmailWithAttachment(File file, String body, String subject, String to, String[] cc,
                                        String fileName) {
        if (!StringUtils.isEmpty(to)) {
            MimeMessagePreparator preparator = new MimeMessagePreparator() {
                public void prepare(MimeMessage mimeMessage) throws Exception {
                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                    helper.setSubject(subject);
                    helper.setTo(to);
                    helper.setText(body, true);
                    helper.setFrom(environment.getProperty("spring.mail.username"));
                    if (cc != null && !CollectionUtils.isEmpty(Arrays.asList(cc)))
                        helper.setCc(cc);
                    helper.addAttachment(fileName, file);
                }
            };
            javaMailSender.send(preparator);
            log.info("Mail send successfully");
        } else
            log.info("No recipients found to send mail");
    }
}
