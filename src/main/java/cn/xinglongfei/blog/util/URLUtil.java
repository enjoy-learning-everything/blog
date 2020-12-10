package cn.xinglongfei.blog.util;

import cn.xinglongfei.blog.config.AliyunOSSConfigConstant;
import cn.xinglongfei.blog.enums.ImageLinkEnum;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Phoenix on 2020/11/23
 */
public class URLUtil {


    /**
     * 验证是否是图片链接
     * @param imageUrl 待验证的图片链接
     * @return 验证结果
     */
    public static String verifyImageUrl(String imageUrl){
        if (imageUrl == null || imageUrl.equals("")) {
            return ImageLinkEnum.URL_IS_NULL.getDesc();
        }
        if (!FileTypeUtil.isImageUrl(imageUrl)) {
            return ImageLinkEnum.NOT_A_IMAGE_URL_LINK_OR_ACCESS_DENIED.getDesc();
        } else {
            return ImageLinkEnum.URL_IS_VERIFIED.getDesc();
        }
    }

    /**
     * 获取给定链接对应的OSS对象存储的文件的文件名
     * @param url OSS文件链接
     * @return 文件名
     */
    public static String getFileName(String url){
        String fileName = "";
        String suffixes="jpeg|jpg|png|bmp|gif";
        Pattern pat= Pattern.compile("[\\w.-]+[.]("+suffixes+")");//正则判断
        Matcher mc=pat.matcher(url);//条件匹配
        while(mc.find()) {
            fileName= mc.group();
        }
        return fileName;
    }

    /**
     * 获取给定链接对应的OSS对象存储的文件所在的文件夹路径
     * @param url OSS文件链接
     * @return 所在文件夹路径
     */
    public static String getFilePath(String url){
        String filePath = "";
        String suffixes=getFileName(url);
        String regx = "(?<="+ AliyunOSSConfigConstant.getDomainName()+"/)(.*?)(?=/"+suffixes+")";
        System.out.println(regx);
        Pattern pat= Pattern.compile(regx);//正则判断
        Matcher mc=pat.matcher(url);//条件匹配
        while(mc.find()) {
            filePath= mc.group();
        }
       return filePath;
    }


    /**
     * 检测链接是否是OSS文件链接（域名为:image.xinglongfei.cn）
     * @param url 链接
     * @return 是/否
     */
    public static boolean isOSSFile(String url){
        boolean result = false;
        String suffixes=getFileName(url);
        String regx = "(?<="+ AliyunOSSConfigConstant.getDomainName()+"/)(.*?)(?=/"+suffixes+")";
        System.out.println(regx);
        Pattern pat= Pattern.compile(regx);//正则判断
        Matcher mc=pat.matcher(url);//条件匹配
        if(mc.find()) {
            result = true;
        }
        return result;
    }


    /**
     * URL解码
     * @param str 待解码的URL
     * @return 解码完成后的url
     */
    public static String getURLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
