package cn.xinglongfei.blog.Controller;

import cn.xinglongfei.blog.service.LinkCategoryService;
import cn.xinglongfei.blog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by Phoenix on 2020/11/30
 */
@Controller
public class LinkShowController {

    @Autowired
    private LinkService linkService;
    @Autowired
    private LinkCategoryService linkCategoryService;

}
