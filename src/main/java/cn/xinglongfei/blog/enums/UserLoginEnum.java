package cn.xinglongfei.blog.enums;

/**
 * Created by Phoenix on 2020/11/23
 */
public enum UserLoginEnum {
    USER_NAME_IS_NUll("user_name_is_null", "用户名不能为空"),
    USER_PASSWORD_IS_NULL("user_password_is_null", "密码不能为空"),
    USER_NAME_OR_PASSWORD_IS_FAIL("user_name_or_password_is_fail", "用户名和密码不能为空"),
    USER_VALIDATE_CODE_IS_FAIL("user_validate_code_is_fail", "验证码错误"),
    USER_LOGIN_SUCCESS("user_login_success", "登录成功");

    private String value;
    private String desc;

    UserLoginEnum(String value, String desc) {
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
