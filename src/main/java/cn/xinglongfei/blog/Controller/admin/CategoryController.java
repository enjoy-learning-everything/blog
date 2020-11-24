package cn.xinglongfei.blog.Controller.admin;

import cn.xinglongfei.blog.po.Category;
import cn.xinglongfei.blog.service.CategoryService;
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
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/categories")
    public String categories(@PageableDefault(size = 10, sort = {"id"},
            direction = Sort.Direction.ASC) Pageable pageable, Model model) {
        model.addAttribute("page", categoryService.listCategory(pageable));
        return "admin/categories";
    }

    @GetMapping("/categories/newCategory")
    public String newCategory(Model model) {
        model.addAttribute("category", new Category());
        return "admin/newCategory";
    }

    @GetMapping("/categories/{id}/edit")
    public String editCategory(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.getCategory(id));
        return "admin/newCategory";
    }


    @PostMapping("/categories")
    public String newCategoryPost(@Valid Category category, BindingResult result, RedirectAttributes attributes) {
        //重复值校验
        Category categoryFind = categoryService.getCategoryByName(category.getName());
        if (categoryFind != null) {
            result.rejectValue("name", "nameError", "数据库中已有该分类");
        }
        //非空校验
        if (result.hasErrors()) {
            return "admin/newCategory";
        }
        //校验通过后存储该值
        Category categoryTemp = categoryService.saveCategory(category);
        if (categoryTemp == null) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/{id}")
    public String editCategoryPost(@Valid Category category, BindingResult result,
                                   @PathVariable Long id, RedirectAttributes attributes) {
        //重复值校验
        Category categoryFind = categoryService.getCategoryByName(category.getName());
        if (categoryFind != null) {
            result.rejectValue("name", "nameError", "数据库中已有该分类");
        }
        //非空校验
        if (result.hasErrors()) {
            return "admin/newCategory";
        }
        //校验通过后存储该值
        Category categoryTemp = categoryService.updateCategory(id,category);
        if (categoryTemp == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/categories";
    }


    @GetMapping("/categories/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        categoryService.deleteCategory(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/categories";
    }
}
