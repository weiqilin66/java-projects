package org.wayne;

import com.netflix.discovery.converters.Auto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@SpringBootTest
class SmtpEmailApplicationTests {

    @Autowired
    JavaMailSender javaMailSender;
    @Test
    public void sendSimpleMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("这是一封测试邮件");//设置邮件主题

        message.setFrom("822742491@qq.com");//设置邮件发送者

        message.setTo("linweiqi45@vip.qq.com");//设置邮件接收者，可以有多个接收者

        message.setCc("601048345@qq.com");//设置邮件抄送人，可以有多个抄送人
//        message.setBcc("14xxxxx098@qq.com");//设置隐秘抄送人，可以有多个

        message.setSentDate(new Date());//设置邮件发送日期

        message.setText("这是测试邮件的正文");//设置邮件的正文

        javaMailSender.send(message);
    }
    /**
     * 使用thymeleaf模板
     */
    @Autowired
    TemplateEngine templateEngine;
    @Test
    public void sendThymeleafMail() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("这是一封测试邮件");
        helper.setFrom("822742491@qq.com");
        helper.setTo("linweiqi45@vip.qq.com");

        helper.setSentDate(new Date());
        Context context = new Context();
        context.setVariable("username", "javaboy");
        context.setVariable("num","000001");
        context.setVariable("salary", "99999");
        String process = templateEngine.process("demo.html", context);
        helper.setText(process,true);
        javaMailSender.send(mimeMessage);
    }
}
