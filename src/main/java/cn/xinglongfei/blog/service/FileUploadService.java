package cn.xinglongfei.blog.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Phoenix on 2020/11/23
 */

public interface FileUploadService {

    /**
     * 上传文件到阿里云对象存储
     *
     * @return
     */
    public String upLoadFileToALiYunOSS(MultipartFile file, String locationPath, String DiyFilename) throws IOException;


    public String upLoadFileToALiYunOSS(String imageUrl, String locationPath, String DiyFilename) throws IOException;

    /**
     * 下载阿里云对象存储文件
     * @return
     */
//    public String downloadFile();
}
