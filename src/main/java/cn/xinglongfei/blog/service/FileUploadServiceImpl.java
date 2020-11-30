package cn.xinglongfei.blog.service;

import cn.xinglongfei.blog.enums.FileUploadEnum;
import cn.xinglongfei.blog.enums.ImageLinkEnum;
import cn.xinglongfei.blog.util.AliyunOSSUtil;
import cn.xinglongfei.blog.util.FileTypeUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Phoenix on 2020/11/23
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Override
    public String upLoadFileToALiYunOSS(MultipartFile file, String locationPath, String DiyFilename) throws IOException {
        //获取文件名
        String filename = file.getOriginalFilename();
        System.out.println(filename);

        if (file == null) {
            return FileUploadEnum.FILE_IS_NULL.getValue();
        } else {
            if (file.getSize() > 2 * 1024 * 1024) {
                return FileUploadEnum.FILE_SIZE_EXCEEDS_2MB.getValue();
            } else {
                if ("".equals(filename.trim())) {
                    return FileUploadEnum.FILE_NAME_IS_NULL.getValue();
                } else {
                    File newFile = new File(filename);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(file.getBytes());
                    os.close();
                    file.transferTo(newFile);
                    //如果是真的图片文件
                    if (!FileTypeUtil.isImageFIle(newFile)) {
                        return FileUploadEnum.NOT_A_IMAGE_FILE.getValue();
                    } else {
                        //执行上传操作
                        String uploadUrl = AliyunOSSUtil.upLoadFile(newFile, locationPath, DiyFilename);
                        if (uploadUrl == null || uploadUrl.equals("")) {
                            return FileUploadEnum.UPLOAD_FILE_FAIL.getValue();
                        }
                        System.out.println(newFile.exists());
                        if (newFile.exists()) {
                            boolean deleteResult = newFile.delete();
                            if (!deleteResult) {
                                return FileUploadEnum.TEMP_FILE_TEMP_DELETE_FIAL.getValue();
                            }
                        }
                    }
                }
            }
        }
        return FileUploadEnum.UPLOAD_FILE_SUCCESS.getValue();
    }

    @Override
    public String upLoadFileToALiYunOSS(String imageUrl, String locationPath, String DiyFilename) throws IOException {
        if (imageUrl == null || imageUrl.equals("")) {
            return ImageLinkEnum.URL_IS_NULL.getValue();
        }
        if (!FileTypeUtil.isImageUrl(imageUrl)) {
            return ImageLinkEnum.NOT_A_IMAGE_URL_LINK_OR_ACCESS_DENIED.getValue();
        } else {
            String uploadUrl = AliyunOSSUtil.upLoadFile(imageUrl, locationPath, DiyFilename);
            if (uploadUrl.equals("")) {
                return ImageLinkEnum.UPLOAD_BY_URL_FAIL.getValue();
            }
        }
        return ImageLinkEnum.UPLOAD_BY_URL_SUCCESS.getValue();
    }

//    @Override
//    public String downloadFile() {
//        return null;
//    }
}
