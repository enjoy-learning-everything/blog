package cn.xinglongfei.blog.Controller.admin;

import cn.xinglongfei.blog.config.AliyunOSSConfigConstant;
import cn.xinglongfei.blog.enums.FilePathEnum;
import cn.xinglongfei.blog.enums.FileUploadEnum;
import cn.xinglongfei.blog.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Phoenix on 2020/12/6
 */
@Controller
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;


    //Markdown编辑器上传图片
    @ResponseBody
    @PostMapping(value = "/editorUpload")
    public Map<String, Object> markdownImageUpload(@RequestParam(value = "editormd-image-file", required = false) MultipartFile file,
                                                   @RequestParam(value = "localPath", required = false) String locaPath, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //生成随机文件名
        String randomName = UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //保存
        try {
            //判断文件是否合法，合法则直接上传，不合法则返回错误信息
            String result = fileUploadService.upLoadFileToALiYunOSS(file, FilePathEnum.PAGE.getPath() + "/" + locaPath, randomName);
            if (result.equals(FileUploadEnum.UPLOAD_FILE_SUCCESS.getValue())) {
                resultMap.put("success", 1);
                resultMap.put("message", "上传成功！");
                resultMap.put("url", "http://"+AliyunOSSConfigConstant.getDomainName() +"/"+ FilePathEnum.PAGE.getPath() + "/" + locaPath +"/"+ randomName);
            } else {
                resultMap.put("success", 0);
                resultMap.put("message", FileUploadEnum.getDescByValue(result));
            }
        } catch (Exception e) {
            resultMap.put("success", 0);
            resultMap.put("message", "上传失败！");
            e.printStackTrace();
        }
        System.out.println(resultMap.get("success"));
        return resultMap;
    }
}
