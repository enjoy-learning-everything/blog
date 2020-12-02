package cn.xinglongfei.blog.dao;

import cn.xinglongfei.blog.po.Link;
import cn.xinglongfei.blog.po.LinkCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Phoenix on 2020/11/30
 */
public interface LinkResposiory extends JpaRepository<Link,Long>, JpaSpecificationExecutor<Link> {

    /**
     * 查询出对应分类下的所有外链
     * @param linkCategory 分类
     * @return 符合要求的外链List
     */
    List<Link> findAllByLinkCategory(LinkCategory linkCategory);
}
