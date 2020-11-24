package cn.xinglongfei.blog.util;

import cn.xinglongfei.blog.enums.FileTypeListEnum;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Phoenix on 2020/11/23
 */
public class FileTypeUtil {
    public static boolean isImageFIle(File file) {
        try {
            // 通过ImageReader来解码这个file并返回一个BufferedImage对象
            // 如果找不到合适的ImageReader则会返回null，我们可以认为这不是图片文件
            Image image = ImageIO.read(file);
            return image != null;
        } catch (IOException ex) {
            // 或者在解析过程中报错，也返回false
            return false;
        }
    }


    /**
     * @param netUrl 图片地址
     * @return 是否为图片或是否可正常访问
     */
    public static boolean isImageUrl(String netUrl) {
        try {
            URL url = new URL(netUrl);
            HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
            //设置连接主机超时（单位：毫秒）
            httpUrl.setConnectTimeout(10000);
            //设置从主机读取数据超时（单位：毫秒）
            httpUrl.setReadTimeout(10000);
            httpUrl.connect();
            InputStream inputStream = httpUrl.getInputStream();
            FileTypeListEnum fileType = getFileType(inputStream);
            //文件类型为空
            if (fileType == null) {
                return false;
            } else {
                //判断文件类型是否为BMP、GIF、PNG、JPEG中的任何一种
                return fileType.equals(FileTypeListEnum.BMP) || fileType.equals(FileTypeListEnum.GIF)
                        || fileType.equals(FileTypeListEnum.JPEG) || fileType.equals(FileTypeListEnum.PNG);
            }
        } catch (IOException e) {
            //无法读取的链接一并视为非图片链接
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 根据文件流获取文件类型
     *
     * @param is 输入的文件流
     * @return 返回文件类型枚举类，如果类型不在枚举中，则返回null
     * @throws IOException 读取异常
     */
    public static FileTypeListEnum getFileType(InputStream is) throws IOException {
        byte[] src = new byte[28];
        is.read(src, 0, 28);
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v).toUpperCase();
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        FileTypeListEnum[] fileTypes = FileTypeListEnum.values();
        //判断文件类型属于枚举类中的哪一种
        for (FileTypeListEnum fileType : fileTypes) {
            if (stringBuilder.toString().startsWith(fileType.getValue())) {
                return fileType;
            }
        }
        return null;
    }
}
