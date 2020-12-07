package cn.xinglongfei.blog.service;

import cn.xinglongfei.blog.NotFoundException;
import cn.xinglongfei.blog.dao.CategoryRepository;
import cn.xinglongfei.blog.po.Category;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Phoenix on 2020/11/17
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Long countCategory() {
        return categoryRepository.count();
    }

    @Transactional
    @Override
    public Category saveCategory(Category category) {
        ;
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> listCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }


    @Override
    public Category getCategory(Long id) {
        return categoryRepository.getOne(id);
    }


    @Override
    public Page<Category> listCategory(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public List<Category> listCategoryTop(Integer size) {
        List<Category> types = categoryRepository.findAll();
        List<Category> typeList =new ArrayList<>();
        int size1 =types.size();
        for (int i=0;i<size1;i++){
            for (int j=0;j<types.get(i).getBlogs().size();j++){
                if (!types.get(i).getBlogs().get(j).isPublished()){
                    types.get(i).getBlogs().remove(j);
                    //删除了后需要重置游标，防止漏掉
                    j--;
                }
            }
        }
        Collections.sort(types);
        if (size>size1){
            size=size1;
        }
        for (int i=0;i<size;i++){
            typeList.add(types.get(i));
        }
        return typeList;
    }

    @Transactional
    @Override
    public Category updateCategory(Long id, Category category) {
        Category categoryTemp = categoryRepository.getOne(id);
        if (categoryTemp == null) {
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(category, categoryTemp);
        return categoryRepository.save(categoryTemp);
    }

    @Transactional
    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
