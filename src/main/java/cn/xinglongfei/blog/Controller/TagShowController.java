package cn.xinglongfei.blog.Controller;

import cn.xinglongfei.blog.po.Tag;
import cn.xinglongfei.blog.service.BlogService;
import cn.xinglongfei.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by Phoenix on 2020/11/19
 */
@Controller
public class TagShowController {

    @Autowired
    private TagService TagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 10, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id, Model model) {
        List<Tag> tags = TagService.listTagTop(1000);
        if (id == -1) {
            id = tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.listBlog(id, pageable));
        model.addAttribute("activeTagId",id);
        return "tags";
    }
}
