package cn.xinglongfei.blog.dao;

import cn.xinglongfei.blog.po.LinkCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Phoenix on 2020/11/30
 */
public interface LinkCategoryResposiory extends JpaRepository<LinkCategory,Long> {

    LinkCategory findByName(String name);

    LinkCategory findByPriority(Long priority);

}
