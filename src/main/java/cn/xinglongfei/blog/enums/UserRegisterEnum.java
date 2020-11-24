package cn.xinglongfei.blog.enums;

/**
 * Created by Phoenix on 2020/11/23
 */
public enum UserRegisterEnum {

    USER_NAME_IS_EXIST("user_name_is_exist","用户名已存在"),
    USER_NAME_IS_NUll("user_name_is_null","用户名注册不能为空"),
    USER_PASSWORD_IS_NULL("user_password_is_null","密码注册不能少于六位"),
    USER_PASSWORD_IS_UNLIKE("user_password_is_unlike","确认密码不一致"),
    USER_VALIDATE_CODE_IS_FAIL("user_validate_code_is_fail","验证码错误"),
    USER_REGISTER_FAIL("user_register_fail","用户注册失败"),
    USER_REGISTER_SUCCESS("user_register_success","用户注册成功");



    private String value;
    private String desc;

    // 添加构造函数的 快捷键 是 alt + shift + s
    private UserRegisterEnum(String value, String desc) {
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
