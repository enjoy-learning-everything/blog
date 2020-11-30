package cn.xinglongfei.blog.dao;

import cn.xinglongfei.blog.po.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Phoenix on 2020/11/30
 */
public interface LinkResposiory extends JpaRepository<Link,Long>, JpaSpecificationExecutor<Link> {
}
