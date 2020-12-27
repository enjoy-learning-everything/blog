package cn.xinglongfei.blog.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Created by Phoenix on 2020/11/23
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserEnum {

    OLD_PASSWORD_IS_NULL("old_password_is_null","旧密码为空","400"),
    NEW_PASSWORD_IS_NULL("new_password_is_null","新密码为空","400"),
    USER_UPDATE_PASSWORD_SUCCESS("user_update_password_success","修改密码成功","200"),
    USER_UPDATE_PASSWORD_FAILED("user_update_password_failed","修改密码失败","400"),
    PASSWORD_ERROR("password_error","旧密码错误","400");

    private String value;
    private String desc;
    private String status;

    private UserEnum(String value, String desc,String status) {
        this.value = value;
        this.desc = desc;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    /**
//     * 重写toString，单个枚举类转化成json
//     * @return
//     */
//    @Override
//    public String toString() {
//        JSONObject object = new JSONObject();
//        object.put("value",value);
//        object.put("desc",desc);
//        object.put("status",status);
//        System.out.println(object.toString());
//        return object.toString();
//    }
}
