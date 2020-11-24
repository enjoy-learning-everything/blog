package cn.xinglongfei.blog.service;

import cn.xinglongfei.blog.po.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Phoenix on 2020/11/17
 */
public interface CategoryService {

    Long countCategory();

    Category saveCategory(Category category);

    Category getCategory(Long id);

    Category getCategoryByName(String name);

    Page<Category> listCategory(Pageable pageable);

    List<Category> listCategory();

    List<Category> listCategoryTop(Integer size);

    Category updateCategory(Long id,Category category);

    void deleteCategory(Long id);
}
