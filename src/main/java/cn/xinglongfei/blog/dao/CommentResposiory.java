package cn.xinglongfei.blog.dao;

import cn.xinglongfei.blog.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Phoenix on 2020/11/19
 */
public interface CommentResposiory extends JpaRepository<Comment,Long> {

    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
}
