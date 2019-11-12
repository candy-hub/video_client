package com.video.config;


import com.video.dao.UserRepository;
import com.video.domain.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Component
public class ReceiverMessageConfig {

    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private TemplateEngine templateEngine;

    @Resource
    private UserRepository userRepository;

    @Value("${spring.mail.username}")
    private String MAIL_SENDER;

    @RabbitListener(queues = "mail")
    public void receive(String userId){
        int userIdReceive = Integer.parseInt(userId);
        //创建邮件正文
        Context context = new Context();
        context.setVariable("userId",userIdReceive);
        String emailContent = templateEngine.process("emailTemplate", context);

        MimeMessage mimeMailMessage = null;
        try {
            mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(MAIL_SENDER);
            User user = userRepository.findById(userIdReceive).get();
            mimeMessageHelper.setTo(user.getUserEmail());
            mimeMessageHelper.setSubject("用户激活账号");
            mimeMessageHelper.setText(emailContent,true);
            javaMailSender.send(mimeMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
