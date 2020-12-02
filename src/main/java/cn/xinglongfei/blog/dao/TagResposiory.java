package cn.xinglongfei.blog.dao;

import cn.xinglongfei.blog.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Phoenix on 2020/11/18
 */
public interface TagResposiory extends JpaRepository <Tag,Long>{

    Tag findByName(String name);

    /**
     * 根据pageable对象查询符合要求的标签List
     * @param pageable 分页查询，封装了当前页，每页显示的条数，排序方式
     * @return 符合要求的标签List
     */
    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
