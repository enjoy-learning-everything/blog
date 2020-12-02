package cn.xinglongfei.blog.Controller;

import cn.xinglongfei.blog.log.MyLog;
import cn.xinglongfei.blog.service.LinkCategoryService;
import cn.xinglongfei.blog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Phoenix on 2020/11/30
 */
@Controller
public class LinkShowController {

    @Autowired
    private LinkService linkService;
    @Autowired
    private LinkCategoryService linkCategoryService;

    @MyLog(operation = "【访客端】跳转页面：归档列表")
    @GetMapping("/links")
    public String archives(Model model) {
        model.addAttribute("linksMap",linkService.archiveLink());
        return "links";
    }


}
