package cn.xinglongfei.blog.dao;

import cn.xinglongfei.blog.po.Category;
import org.springframework.data.domain.Pageable;
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

    /**
     * 根据pageable对象查询符合要求的分类List
     * @param pageable 分页查询，封装了当前页，每页显示的条数，排序方式
     * @return 符合要求的分类List
     */
    @Query("select c from Category c")
    List<Category> findTop(Pageable pageable);
}
