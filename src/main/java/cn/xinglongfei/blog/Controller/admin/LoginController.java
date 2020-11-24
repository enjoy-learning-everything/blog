package cn.xinglongfei.blog.Controller.admin;

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



    @GetMapping
    public String loginPage() {
        return "admin/login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session, RedirectAttributes attributes, HttpServletRequest request) {
        if (!CodeUtil.checkVerifyCode(request)) {
            attributes.addFlashAttribute("message", "验证码错误！");
            return "redirect:/admin";
        } else {
            User user = userService.checkUser(username, password);
            if (user != null) {
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
