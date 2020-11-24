package cn.xinglongfei.blog.util;

import cn.xinglongfei.blog.config.AliyunOSSConfigConstant;
import com.aliyun.oss.*;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Phoenix on 2020/11/22
 */
@Component
public class AliyunOSSUtil {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AliyunOSSUtil.class);
    private static String WEB_FILE_URL;
    private static String bucketName = AliyunOSSConfigConstant.getBucketName();
    private static String endpoint = AliyunOSSConfigConstant.getEndPoint();
    private static String accessKeyId = AliyunOSSConfigConstant.getKeyId();
    private static String accessKeySecret = AliyunOSSConfigConstant.getKeySecret();


    /**
     * 上传本地文件。
     *
     * @param file         需要上传的文件路径
     * @param locationPath 上传的文件所在的文件夹目录，若传入空字符串则不新建文件夹，直接将文件存在容器根目录中
     *                     可以使用"/"来建立多级目录
     * @param DiyFilename  自定义名字
     * @return 返回上传结果，上传失败则返回空字符串
     */
    public static String upLoadFile(File file, String locationPath, String DiyFilename) {
        //文件在服务器端的存储路径
        String webDiskPath = locationPath + "/" + DiyFilename;

        logger.info("------OSS本地文件上传开始--------" + file.getName());

        // 创建ClientConfiguration实例，您可以按照实际情况修改默认参数。
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        // 设置是否支持CNAME。CNAME是指将自定义域名绑定到存储空间上。
        conf.setSupportCname(true);
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret, conf);

        try {
            // 上传文件
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName, webDiskPath, file));
            // 设置存储空间权限(公开读)
            ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            if (result == null) {
                return "";
            }
            logger.info("------OSS本地文件上传成功------" + webDiskPath);
            return result.toString();
        } catch (OSSException oe) {
            logger.error(oe.getMessage());
            return "";
        } catch (ClientException ce) {
            logger.error(ce.getErrorMessage());
            return "";
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     * 上传网络文件。
     *
     * @param fileUrl      需要上传的文件URL
     * @param locationPath 上传的文件所在的文件夹目录，若传入空字符串则不新建文件夹，直接将文件存在容器根目录中
     *                     可以使用"/"来建立多级目录
     * @param DiyFilename  自定义名字
     * @return 返回上传结果，上传失败则返回空字符串
     */
    public static String upLoadFile(String fileUrl, String locationPath, String DiyFilename) {
        //文件在服务器端的存储路径
        String webDiskPath = locationPath + "/" + DiyFilename;

        logger.info("------OSS通过地址上传文件开始--------");

        // 创建ClientConfiguration实例，您可以按照实际情况修改默认参数。
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        // 设置是否支持CNAME。CNAME是指将自定义域名绑定到存储空间上。
        conf.setSupportCname(true);
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret, conf);
        // 上传网络流。
        InputStream inputStream = null;
        try {
            inputStream = new URL(fileUrl).openStream();
            PutObjectResult result = ossClient.putObject(bucketName, webDiskPath, inputStream);
            System.out.println(result);
            logger.info("------OSS通过地址上传文件成功--------");
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } catch (OSSException oe) {
            logger.error(oe.getMessage());
            return "";
        } catch (ClientException ce) {
            logger.error(ce.getErrorMessage());
            return "";
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return "WEB_FILE_URL";
    }


    public static String deleteFile(String ossFilePathAndFileName) {
        //获取相对于根目录的地址
        String filePathAndName = URLUtil.getFilePath(ossFilePathAndFileName)+"/"+URLUtil.getFileName(ossFilePathAndFileName);
        logger.info("------OSS文件删除开始--------");

        // 创建ClientConfiguration实例，您可以按照实际情况修改默认参数。
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        // 设置是否支持CNAME。CNAME是指将自定义域名绑定到存储空间上。
        conf.setSupportCname(true);
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret, conf);
        if(isFileExist(filePathAndName)){
            ossClient.deleteObject(bucketName, filePathAndName);
        }
        logger.info("------OSS文件删除结束--------");
        ossClient.shutdown();
        return "";
    }


    public static boolean isFileExist(String ossFilePathAndFileName) {
        logger.info("------OSS文件存在性检测开始--------");
        // 创建ClientConfiguration实例，您可以按照实际情况修改默认参数。
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        // 设置是否支持CNAME。CNAME是指将自定义域名绑定到存储空间上。
        conf.setSupportCname(true);
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret, conf);
        boolean result = ossClient.doesObjectExist(bucketName, ossFilePathAndFileName);
        logger.info("------OSS文件存在性检测结束--------存在："+result);
        // 关闭OSSClient。
        ossClient.shutdown();
        return result;
    }


    /**
     * 通过文件名获取/下载文件
     *
     * @param objectName    要下载的文件名
     * @param localFileName 本地要创建的文件名
     */
    public static void downloadFile(String objectName, String localFileName) {

        // 创建OSSClient实例。
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        conf.setSupportCname(true);
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret, conf);
        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(localFileName));
        // 关闭OSSClient。
        ossClient.shutdown();
    }

}
