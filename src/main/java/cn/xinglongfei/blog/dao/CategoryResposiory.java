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

    Category findByName(String name);

    @Query("select c from Category c")
    List<Category> findTop(Pageable pageable);
}
