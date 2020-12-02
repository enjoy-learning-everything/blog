package cn.xinglongfei.blog.Controller;


import cn.xinglongfei.blog.log.MyLog;
import cn.xinglongfei.blog.service.BlogService;
import cn.xinglongfei.blog.service.CategoryService;
import cn.xinglongfei.blog.service.TagService;
import cn.xinglongfei.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Phoenix on 2020/11/14
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;

    @MyLog(operation = "【访客端】跳转页面：主页")
    @GetMapping("/")
    public String index(@PageableDefault(size = 10, sort = {"createTime"},
            direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("categories", categoryService.listCategoryTop(6));
        model.addAttribute("categoryCount", categoryService.countCategory());
        model.addAttribute("tags", tagService.listTagTop(6));
        model.addAttribute("tagCount", tagService.countTag());
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(6));
        return "index";
    }

    @MyLog(operation = "【访客端】跳转页面：搜索结果")
    @PostMapping("/search")
    public String search(@RequestParam String query, @PageableDefault(size = 10, sort = {"createTime"},
            direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        //根据用户输入的内容（query）查询出内容和标题中包含该字段的博客
        model.addAttribute("page", blogService.listBlog("%"+query+"%",pageable));
        return "search";
    }

    @MyLog(operation = "【访客端】跳转页面：博客详情")
    @GetMapping("/blogs/{id}")
    public String blog(@PathVariable Long id, Model model) {
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }
}
