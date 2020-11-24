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

    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
