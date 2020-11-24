package cn.xinglongfei.blog.enums;

/**
 * Created by Phoenix on 2020/11/23
 */
public enum UserEnum {

    USER_UPDATE_PASSWORD_SUCCESS("user_update_password_success","修改密码成功");

    private String value;
    private String desc;

    private UserEnum(String value, String desc) {
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
