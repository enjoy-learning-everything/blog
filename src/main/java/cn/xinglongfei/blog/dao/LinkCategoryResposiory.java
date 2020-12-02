package cn.xinglongfei.blog.dao;

import cn.xinglongfei.blog.po.LinkCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Phoenix on 2020/11/30
 */
public interface LinkCategoryResposiory extends JpaRepository<LinkCategory,Long> {

    /**
     * 根据名字查询外链分类
     * @param name 名字
     * @return 外链分类
     */
    LinkCategory findByName(String name);


}
