package cn.xinglongfei.blog.Controller.admin;

import cn.xinglongfei.blog.log.MyLog;
import cn.xinglongfei.blog.po.LinkCategory;
import cn.xinglongfei.blog.service.LinkCategoryService;
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
 * Created by Phoenix on 2020/11/30
 */
@Controller
@RequestMapping("/admin")
public class LinkCategoryController {

    @Autowired
    private LinkCategoryService linkCategoryService;

    @MyLog(operation = "【管理端】跳转页面：外链分类列表")
    @GetMapping("/linkCategories")
    public String linkCategories(@PageableDefault(size = 10, sort = {"priority"},
            direction = Sort.Direction.ASC) Pageable pageable, Model model) {
        model.addAttribute("page", linkCategoryService.listLinkCategory(pageable));
        return "admin/linkCategories";
    }

    @MyLog(operation = "【管理端】跳转页面：新增外链分类",type = "跳转")
    @GetMapping("/linkCategories/newLinkCategory")
    public String newLinkCategory(Model model) {
        model.addAttribute("linkCategory", new LinkCategory());
        return "admin/newLinkCategory";
    }

    @MyLog(operation = "【管理端】跳转页面：修改外链分类")
    @GetMapping("/linkCategories/{id}/edit")
    public String editLinkCategory(@PathVariable Long id, Model model) {
        model.addAttribute("linkCategory", linkCategoryService.getLinkCategory(id));
        return "admin/newLinkCategory";
    }

    @MyLog(operation = "【管理端】访问接口：新增外链分类",type = "新增")
    @PostMapping("/linkCategories")
    public String newLinkCategoryPost(@Valid LinkCategory linkCategory, BindingResult result, RedirectAttributes attributes) {
        //重复值校验
        LinkCategory linkCategoryFindByName = linkCategoryService.getLinkCategoryByName(linkCategory.getName());
        if (linkCategoryFindByName != null ) {
            result.rejectValue("name", "nameError", "数据库中已有该分类名称");
        }
        //非空校验
        if (result.hasErrors()) {
            return "admin/newLinkCategory";
        }
        //校验通过后存储该值
        LinkCategory LinkCategoryTemp = linkCategoryService.saveLinkCategory(linkCategory);
        if (LinkCategoryTemp == null) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/linkCategories";
    }

    @MyLog(operation = "【管理端】访问接口：修改外链分类",type = "修改")
    @PostMapping("/linkCategories/{id}")
    public String editLinkCategoryPost(@Valid LinkCategory linkCategory, BindingResult result,
                              @PathVariable Long id, RedirectAttributes attributes) {
        //重复值校验
        LinkCategory linkCategoryFind = linkCategoryService.getLinkCategoryByName(linkCategory.getName());
        if (linkCategoryFind != null && !linkCategoryFind.getId().equals(linkCategory.getId())) {
            result.rejectValue("name", "nameError", "数据库中已有该标签");
        }
        //非空校验
        if (result.hasErrors()) {
            return "admin/newLinkCategory";
        }
        //校验通过后存储该值
        LinkCategory LinkCategoryTemp = linkCategoryService.updateLinkCategory(id,linkCategory);
        if (LinkCategoryTemp == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/linkCategories";
    }

    @MyLog(operation = "【管理端】访问接口：删除外链分类",type = "删除")
    @GetMapping("/linkCategories/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        linkCategoryService.deleteLinkCategory(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/linkCategories";
    }

}
