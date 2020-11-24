package cn.xinglongfei.blog.Controller.admin;

import cn.xinglongfei.blog.po.Blog;
import cn.xinglongfei.blog.po.User;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by Phoenix on 2020/11/17
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String NEWBLOGPAGE = "admin/newBlog";
    private static final String LISTBLOGPAGE = "admin/blogs";
    private static final String REDIRECT_LISTBLOGPAGE = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;


    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 10, sort = {"createTime"},
            direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("categories", categoryService.listCategory());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return LISTBLOGPAGE;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 10, sort = {"createTime"},
            direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogListTable";
    }

    private void setTypeAndTag(Model model) {
        model.addAttribute("categories", categoryService.listCategory());
        model.addAttribute("tags", tagService.listTag());

    }

    @GetMapping("/blogs/newBlog")
    public String newBlog(Model model) {
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return NEWBLOGPAGE;
    }


    @GetMapping("/blogs/{id}/edit")
    public String editBlog(@PathVariable Long id, Model model) {
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blog);
        return NEWBLOGPAGE;
    }

    @PostMapping("/blogs")
    public String newBlogPost(Blog blog, HttpSession session, RedirectAttributes attributes) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setCategory(categoryService.getCategory(blog.getCategory().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog blogTemp;
        if(blog.getId()==null){
            blogTemp = blogService.saveBlog(blog);
        }else {
            blogTemp = blogService.updateBlog(blog.getId(),blog);
        }
        if (blogTemp == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_LISTBLOGPAGE;
    }


    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_LISTBLOGPAGE;
    }
}
