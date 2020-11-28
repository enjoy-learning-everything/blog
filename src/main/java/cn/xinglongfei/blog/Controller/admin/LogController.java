package cn.xinglongfei.blog.Controller.admin;

import cn.xinglongfei.blog.log.MyLog;
import cn.xinglongfei.blog.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Phoenix on 2020/11/29
 */
@Controller
@RequestMapping("/admin")
public class LogController {

    @Autowired
    private LogService logService;

    @MyLog(operation = "【管理端】跳转页面：日志",type = "跳转")
    @GetMapping("/logs")
    public String logPage(@PageableDefault(size = 50, sort = {"operaTime"},
            direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page", logService.listLog(pageable));
        return "admin/logs";
    }

}
