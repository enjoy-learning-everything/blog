package cn.xinglongfei.blog.enums;

/**
 * Created by Phoenix on 2020/11/23
 */
public enum  ImageLinkEnum {

    URL_IS_NULL("url_is_null","地址为空"),
    URL_IS_TOO_LONG("url_is_too_long","链接过长"),
    NOT_A_IMAGE_URL_LINK_OR_ACCESS_DENIED("not_a_image_url_link_or_access_denied","非图片链接或此链接无权限访问"),
    URL_IS_VERIFIED("url_is_verified","图片地址校验通过"),
    UPLOAD_BY_URL_SUCCESS("upload_by_url_success","依靠链接上传图片成功"),
    UPLOAD_BY_URL_FAIL("upload_by_url_fail","依靠链接上传图片失败");

    private String value;
    private String desc;

    ImageLinkEnum(String value, String desc) {
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


    public static String getValueByDesc(String desc){
        ImageLinkEnum[] imageLinkEnums = values();
        for (ImageLinkEnum imageLinkEnum : imageLinkEnums) {
            if (imageLinkEnum.getDesc().equals(desc)) {
                return imageLinkEnum.getValue();
            }
        }
        return null;
    }


    public static String getDescByValue(String value){
        System.out.println(value);
        ImageLinkEnum[] imageLinkEnums = values();
        for (ImageLinkEnum imageLinkEnum : imageLinkEnums) {
            if (imageLinkEnum.getValue().equals(value)) {
                return imageLinkEnum.getDesc();
            }
        }
        return null;
    }
}
