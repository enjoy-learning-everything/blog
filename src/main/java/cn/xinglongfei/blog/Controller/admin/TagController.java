package cn.xinglongfei.blog.Controller.admin;

import cn.xinglongfei.blog.log.MyLog;
import cn.xinglongfei.blog.po.Tag;
import cn.xinglongfei.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by Phoenix on 2020/11/17
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @MyLog(operation = "【管理端】跳转页面：标签列表")
    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 10, sort = {"id"},
            direction = Sort.Direction.ASC) Pageable pageable, Model model) {
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }

    @MyLog(operation = "【管理端】跳转页面：新增标签",type = "跳转")
    @GetMapping("/tags/newTag")
    public String newTag(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/newTag";
    }

    @MyLog(operation = "【管理端】跳转页面：修改标签")
    @GetMapping("/tags/{id}/edit")
    public String editTag(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/newTag";
    }

    @MyLog(operation = "【管理端】访问接口：新增标签",type = "新增")
    @PostMapping("/tags")
    public String newTagPost(@Valid Tag Tag, BindingResult result, RedirectAttributes attributes) {
        //重复值校验
        Tag TagFind = tagService.getTagByName(Tag.getName());
        if (TagFind != null) {
            result.rejectValue("name", "nameError", "数据库中已有该标签");
        }
        //非空校验
        if (result.hasErrors()) {
            return "admin/newTag";
        }
        //校验通过后存储该值
        Tag TagTemp = tagService.saveTag(Tag);
        if (TagTemp == null) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/tags";
    }

    @MyLog(operation = "【管理端】访问接口：修改标签",type = "修改")
    @PostMapping("/tags/{id}")
    public String editTagPost(@Valid Tag Tag, BindingResult result,
                                   @PathVariable Long id, RedirectAttributes attributes) {
        //重复值校验
        Tag TagFind = tagService.getTagByName(Tag.getName());
        if (TagFind != null && !TagFind.getId().equals(Tag.getId())) {
            result.rejectValue("name", "nameError", "数据库中已有该标签");
        }
        //非空校验
        if (result.hasErrors()) {
            return "admin/newTag";
        }
        //校验通过后存储该值
        Tag TagTemp = tagService.updateTag(id,Tag);
        if (TagTemp == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tags";
    }

    @MyLog(operation = "【管理端】访问接口：修改标签",type = "删除")
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }
}
