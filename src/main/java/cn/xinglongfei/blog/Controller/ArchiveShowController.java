package cn.xinglongfei.blog.Controller;

import cn.xinglongfei.blog.log.MyLog;
import cn.xinglongfei.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Phoenix on 2020/11/19
 */
@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    @MyLog(operation = "【访客端】跳转页面：归档列表")
    @GetMapping("/archives")
    public String archives(Model model) {
        //传入归档Map
        model.addAttribute("archiveMap",blogService.archiveBlog());
        //传入博客总数
        model.addAttribute("blogCount",blogService.countPublishedBlog());
        return "archives";
    }
}
