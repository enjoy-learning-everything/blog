package cn.xinglongfei.blog.Controller.admin;

import cn.xinglongfei.blog.log.MyLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Phoenix on 2020/12/26
 */
@Controller
@RequestMapping("/admin")
public class UserController {

    @MyLog(operation = "【管理端】跳转页面：个人信息")
    @GetMapping("/userInfo")
    public String userInfo(){
        return "admin/userInfo";
    }


    @MyLog(operation = "【管理端】跳转页面：修改密码")
    @GetMapping("/changePassword")
    public String changePassword(){
        return "admin/changePassword";
    }
}
