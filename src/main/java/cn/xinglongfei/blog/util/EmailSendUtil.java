package cn.xinglongfei.blog.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import cn.xinglongfei.blog.config.EmailConfigConstant;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Phoenix on 2020/12/29
 */
@Component
public class EmailSendUtil {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(EmailSendUtil.class);

    //邮件服务商
    private static String emailHost = EmailConfigConstant.getEmailHost();
    //发件端口
    private static String emailPort = EmailConfigConstant.getEmailPort();
    //身份验证
    private static String emailUseAuth = EmailConfigConstant.getEmailUseAuth();
    //使用SSL
    private static String emailUseSSL = EmailConfigConstant.getEmailUseSSL();
    //发件的邮箱
    private static String emailSender = EmailConfigConstant.getEmailSender();
    //Zoho邮箱密码
    private static String emailPassword = EmailConfigConstant.getEmailPassword();

    //发送邮件
    public static boolean sendEmail(String emailAddress, String subject, String content,boolean isHtml) {
        try {
            logger.info("-------------开始获取配置信息-------------------");
            logger.info("-------------host主机： "+emailHost);
            logger.info("-------------端口： "+emailPort);
            MailAccount account = new MailAccount();
            account.setHost(emailHost);
            account.setPort(Integer.parseInt(emailPort));
            account.setAuth(Boolean.parseBoolean(emailUseAuth));
            account.setFrom(emailSender);
            account.setUser(emailSender);
            account.setPass(emailPassword);
            account.setSocketFactoryClass("javax.net.ssl.SSLSocketFactory");
            account.setSslEnable(Boolean.parseBoolean(emailUseSSL));
            logger.info("--------------准备发送邮件----------------");
            MailUtil.send(account, CollUtil.newArrayList(emailAddress), subject, content, isHtml);

            logger.info("--------------发送邮件成功----------------");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
