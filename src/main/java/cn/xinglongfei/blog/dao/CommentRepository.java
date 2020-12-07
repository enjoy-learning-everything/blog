package cn.xinglongfei.blog.dao;

import cn.xinglongfei.blog.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Phoenix on 2020/11/19
 */
public interface CommentRepository extends JpaRepository<Comment,Long> {

    /**
     * 根据博客ID查询出所有一级评论（类似于森林的所有根节点），并按要求排序
     * @param blogId 博客ID
     * @param sort 排序方式
     * @return 符合要求的一级评论
     */
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
}
