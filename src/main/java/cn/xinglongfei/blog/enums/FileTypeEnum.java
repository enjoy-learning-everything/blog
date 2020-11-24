package cn.xinglongfei.blog.enums;

/**
 * Created by Phoenix on 2020/11/23
 */
public enum  FileTypeEnum {

    NOT_A_IMAGE_FILE("not_a_image_file","该文件不是图片文件"),
    NOT_A_COMPRESSED_FILE("not_a_compressed_file","该文件不是压缩文件");

    private String value;
    private String desc;

    FileTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
