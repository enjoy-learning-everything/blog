package cn.xinglongfei.blog.dao;

import cn.xinglongfei.blog.po.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Phoenix on 2020/11/18
 */
public interface TagRepository extends JpaRepository <Tag,Long>{

    Tag findByName(String name);


}
