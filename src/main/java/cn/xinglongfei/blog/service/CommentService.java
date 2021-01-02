package cn.xinglongfei.blog.service;

import cn.xinglongfei.blog.po.Blog;
import cn.xinglongfei.blog.po.Comment;

import java.util.List;

/**
 * Created by Phoenix on 2020/11/19
 */
public interface CommentService {

    Integer countCommentByBlog(Blog blog);

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);

    String getLastAvatar(String email);

    Comment getComment(Long id);

}
