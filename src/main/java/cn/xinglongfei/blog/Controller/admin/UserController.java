package cn.xinglongfei.blog.Controller.admin;

import cn.xinglongfei.blog.enums.UserEnum;
import cn.xinglongfei.blog.log.MyLog;
import cn.xinglongfei.blog.po.User;
import cn.xinglongfei.blog.service.UserService;
import cn.xinglongfei.blog.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * Created by Phoenix on 2020/12/26
 */
@Controller
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @MyLog(operation = "【管理端】跳转页面：个人信息")
    @GetMapping("/userInfo")
    public String userInfo() {
        return "admin/userInfo";
    }


    @MyLog(operation = "【管理端】跳转页面：修改密码")
    @GetMapping("/changePassword")
    public String changePassword() {
        return "admin/changePassword";
    }

    @MyLog(operation = "【管理端】访问接口：修改密码")
    @PostMapping("/changePassword")
    @ResponseBody
    public UserEnum changePasswordPost(@RequestParam String oldPassword,@RequestParam String newPassword,
                                     HttpSession session, RedirectAttributes attributes) {
        if(oldPassword==null || oldPassword.equals("")){
            return UserEnum.OLD_PASSWORD_IS_NULL;
        }
        if(newPassword==null || newPassword.equals("")){
            return UserEnum.NEW_PASSWORD_IS_NULL;
        }
        User sessionUser = (User) session.getAttribute("user");
        User user = userService.getUser(sessionUser.getId());
        if (!Objects.equals(MD5Util.code(oldPassword), user.getPassword())) {
            return UserEnum.PASSWORD_ERROR;
        }else {
           user.setPassword(MD5Util.code(newPassword));
           User userResult =  userService.updateUser(user.getId(),user);
           if(userResult==null){
               return UserEnum.USER_UPDATE_PASSWORD_FAILED;
           }else {
               return UserEnum.USER_UPDATE_PASSWORD_SUCCESS;
           }
        }
    }
}
