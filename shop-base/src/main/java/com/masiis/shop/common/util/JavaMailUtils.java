package com.masiis.shop.common.util;

import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * @Date 2016/6/4
 * @Auther lzh
 */
public class JavaMailUtils {
    private static Logger log = Logger.getLogger(JavaMailUtils.class);
    private static final String DEFAULT_USER_MAIL = "";
    private static final String DEFAULT_USER_MAIL_PWD = "";
    private static final String DEFAULT_TO_MAIL = "";
    private static final String DEFAULT_USER_HOST = "";

    public static void main(String... args){
        sendImageMail("smtp.126.com", "luozhihuicxk@126.com", "lzhCXK0912#)*", "luozhihuicxk@126.com");
    }

    public static boolean sendImageMail(String mailHost, String user, String password, String to){
        Transport ts = null;
        try {
            //1、创建发送邮件session
            Session session = createMailSession(mailHost);
            //2、通过session得到transport对象
            ts = session.getTransport();
            //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
            ts.connect(mailHost, user, password);
            //创建邮件
            MimeMessage message = new MimeMessage(session);
            // 设置邮件的基本信息
            //发件人
            message.setFrom(new InternetAddress(user));
            //收件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            //邮件标题
            message.setSubject("带图片的邮件");
            // 准备邮件数据
            // 准备邮件正文数据
            MimeBodyPart text = new MimeBodyPart();
            text.setContent("这是一封邮件正文带图片<img src='cid:1.jpg'>的邮件", "text/html;charset=UTF-8");
            // 准备图片数据
            MimeBodyPart image = new MimeBodyPart();
            DataHandler dh = new DataHandler(new FileDataSource("D:/wx.jpg"));
            image.setDataHandler(dh);
            image.setContentID("1.jpg");
            // 描述数据关系
            MimeMultipart mm = new MimeMultipart();
            mm.addBodyPart(text);
            mm.addBodyPart(image);
            mm.setSubType("related");
            message.setContent(mm);
            message.saveChanges();
            //将创建好的邮件写入到E盘以文件的形式进行保存
            message.writeTo(new FileOutputStream("E:\\ImageMail.eml"));
            //5、发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            releaseTransport(ts);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            releaseTransport(ts);
            return false;
        }
        return true;
    }


    public static boolean sendSimpleMailBySystem(String subject, String content){
        return sendSimpleMail(subject, content,
                DEFAULT_USER_HOST, DEFAULT_USER_MAIL, DEFAULT_USER_MAIL_PWD, DEFAULT_TO_MAIL);
    }

    public static boolean sendSimpleMail(String subject, String content,
                                            String mailHost, String user, String password,
                                            String to) {
        Transport ts = null;
        try {
            //1、创建发送邮件session
            Session session = createMailSession(mailHost);
            //2、通过session得到transport对象
            ts = session.getTransport();
            //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
            ts.connect(mailHost, user, password);
            //4、创建邮件
            //创建邮件对象
            MimeMessage message = new MimeMessage(session);
            //指明邮件的发件人
            message.setFrom(new InternetAddress(user));
            //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            //邮件的标题
            message.setSubject(subject, "UTF-8");
            //邮件的文本内容
            message.setContent(content, "text/html;charset=UTF-8");
            //5、发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            releaseTransport(ts);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            releaseTransport(ts);
            return false;
        }

        return true;
    }

    public static Session createMailSession(String mailHost){
        Properties prop = new Properties();
        prop.setProperty("mail.host", mailHost);
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        //创建session
        Session session = Session.getInstance(prop);
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        return session;
    }

    private static void releaseTransport(Transport ts){
        if(ts != null){
            try {
                ts.close();
            } catch (MessagingException e1) {
                e1.printStackTrace();
            }
        }
    }

}
