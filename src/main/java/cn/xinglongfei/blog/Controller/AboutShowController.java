package cn.xinglongfei.blog.Controller;

import cn.xinglongfei.blog.log.MyLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Phoenix on 2020/11/19
 */
@Controller
public class AboutShowController {

    @MyLog(operation = "【访客端】跳转页面：关于我",type = "跳转")
    @GetMapping("/about")
    public String about(){
        return "about";
    }
}
