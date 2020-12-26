package cn.xinglongfei.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 描述:阿里云存储(OSS)配置
 * 注意:阿里云主账号AccessKey拥有所有API的访问权限，风险很高。
 * 建议创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
 * Created by Phoenix on 2020/11/22
 */
@Component
public class AliyunOSSConfigConstant {
    //访问方式
    public static String PROTOCOL;
    //仓库名称
    public static String BUCKET_NAME;
    //地域节点
    public static String END_POINT;
    //自定义域名
    public static String DOMAIN_NAME;
    //AccessKey ID
    public static String KEY_ID;
    //Access Key Secret
    public static String KEY_SECRET;

    @Value("${oss.protocol}")
    public void setProtocol(String protocol) {
        PROTOCOL = protocol;
    }

    @Value("${oss.bucketname}")
    public void setBucketName(String bucketName) {
        BUCKET_NAME = bucketName;
    }

    @Value("${oss.endpoint}")
    public void setEndPoint(String endPoint) {
        END_POINT = endPoint;
    }

    @Value("${oss.domainname}")
    public void setDomainName(String domainName) {
        DOMAIN_NAME = domainName;
    }

    @Value("${oss.keyid}")
    public void setKeyId(String keyId) {
        KEY_ID = keyId;
    }

    @Value("${oss.keysecret}")
    public void setKeySecret(String keySecret) {
        KEY_SECRET = keySecret;
    }

    public static String getProtocol() {
        return PROTOCOL;
    }

    public static String getBucketName() {
        return BUCKET_NAME;
    }

    public static String getEndPoint() {
        return END_POINT;
    }

    public static String getDomainName() {
        return DOMAIN_NAME;
    }

    public static String getKeyId() {
        return KEY_ID;
    }

    public static String getKeySecret() {
        return KEY_SECRET;
    }
}
