package com.biao.shop.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;

import javax.annotation.Resource;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * @ClassName MailUtil
 * @Description: TODO
 * @Author Biao
 * @Date 2020/4/20
 * @Version V1.0
 **/
public class MailUtil {

    @Value("${spring.mail.host}")
    private static String host;
    @Value("${spring.mail.port}")
    private static String port;
    @Value("${spring.mail.sendMail}")
    private static String sendMail;
    @Value("${spring.mail.password}")
    private static String myEmailPassword;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private static String fallback; // false
    @Value("${spring.mail.properties.mail.smtp.socketFactory.class}")
    private static String socketFactory;

    @Resource
    private static JavaMailSender mailSender;

    public static boolean sendMailTo(String userName,String receiveMail) throws Exception {
        // JavaMailSender javaMailSender = new JavaMailSenderImpl();
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", port);
        props.setProperty("mail.smtp.auth", "true");
        // 如邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证，则需要使用以下配置项
       /*  SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
                          需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
                          QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)*/
        /*final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", socketFactory);
        props.setProperty("mail.smtp.socketFactory.fallback", fallback);
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);*/

        Session session = Session.getDefaultInstance(props);
        // 设置为debug模式, 可以查看详细的发送 log
        session.setDebug(true);
        MimeMessage message = createMimeMessage(userName,session, sendMail, receiveMail);
        Transport transport = session.getTransport();
        transport.connect(sendMail, myEmailPassword);
        mailSender.send(message);
        // Send the given array of JavaMail MIME messages in batch.
        // void send(MimeMessage... mimeMessages) throws MailException;
        transport.close();
        return true;
    }

    static MimeMessage createMimeMessage(String userName,Session session, String sendMail, String receiveMail) throws Exception {
        // MIME邮件类型，还有一种为简单邮件类型
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendMail, "龙岗汽车", "UTF-8"));
        // 可以增加多个收件人、抄送、密送
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, userName, "UTF-8"));
        // 邮件主题
        message.setSubject("新品信息", "UTF-8");
        // 邮件正文（可以使用html标签）
        message.setContent(userName + "，您好,新品到店，快来体验", "text/html;charset=UTF-8");
        // 设置发件时间
        message.setSentDate(new Date());
        // 保存设置
        message.saveChanges();
        return message;
    }
}
