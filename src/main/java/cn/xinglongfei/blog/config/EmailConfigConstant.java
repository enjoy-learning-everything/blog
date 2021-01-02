package cn.xinglongfei.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Phoenix on 2020/12/29
 */
@Component
public class EmailConfigConstant {
    //邮件服务商
    private static String emailHost;
    //发件端口
    private static String emailPort;
    //身份验证
    private static String emailUseAuth;
    //使用SSL
    private static String emailUseSSL;
    //发件的邮箱
    private static String emailSender;
    //Zoho邮箱密码
    private static String emailPassword;

    @Value("${email.zoho.host}")
    public void setEmailHost(String emailHost) {
        EmailConfigConstant.emailHost = emailHost;
    }

    @Value("${email.zoho.port}")
    public void setEmailPort(String emailPort) {
        EmailConfigConstant.emailPort = emailPort;
    }

    @Value("${email.zoho.auth}")
    public void setEmailUseAuth(String emailUseAuth) {
        EmailConfigConstant.emailUseAuth = emailUseAuth;
    }

    @Value("${email.zoho.ssl}")
    public void setEmailUseSSL(String emailUseSSL) {
        EmailConfigConstant.emailUseSSL = emailUseSSL;
    }

    @Value("${email.zoho.sender}")
    public void setEmailSender(String emailSender) {
        EmailConfigConstant.emailSender = emailSender;
    }

    @Value("${email.zoho.password}")
    public void setEmailPassword(String emailPassword) {
        EmailConfigConstant.emailPassword = emailPassword;
    }

    public static String getEmailHost() {
        return emailHost;
    }

    public static String getEmailPort() {
        return emailPort;
    }

    public static String getEmailUseAuth() {
        return emailUseAuth;
    }

    public static String getEmailUseSSL() {
        return emailUseSSL;
    }

    public static String getEmailSender() {
        return emailSender;
    }

    public static String getEmailPassword() {
        return emailPassword;
    }
}
