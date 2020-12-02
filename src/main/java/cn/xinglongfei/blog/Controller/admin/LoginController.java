package cn.xinglongfei.blog.Controller.admin;

import cn.xinglongfei.blog.log.MyLog;
import cn.xinglongfei.blog.po.User;
import cn.xinglongfei.blog.service.UserService;
import cn.xinglongfei.blog.util.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Phoenix on 2020/11/17
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;


    @MyLog(operation = "【管理端】跳转页面：登录",type = "跳转")
    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

    @MyLog(operation = "【管理端】访问接口：登录核验",type = "登录")
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session, RedirectAttributes attributes, HttpServletRequest request) {
        if (!CodeUtil.checkVerifyCode(request)) {
            attributes.addFlashAttribute("message", "验证码错误！");
            return "redirect:/admin";
        } else {
            //检查用户名和密码是否正确，正确则返回用户名信息
            User user = userService.checkUser(username, password);
            if (user != null) {
                //清空密码后将User放入session
                user.setPassword(null);
                session.setAttribute("user", user);
                return "admin/index";
            } else {
                attributes.addFlashAttribute("message", "用户名或密码错误");
                return "redirect:/admin";
            }
        }

    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }




}
