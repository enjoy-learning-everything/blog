package cn.xinglongfei.blog.dao;

import cn.xinglongfei.blog.po.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Phoenix on 2020/11/17
 */
public interface CategoryResposiory extends JpaRepository<Category,Long> {

    /**
     * 根据名字查询分类
     * @param name 名字
     * @return 分类
     */
    Category findByName(String name);


    @Query("select t from Category t ")
    List<Category> findTop();
}
