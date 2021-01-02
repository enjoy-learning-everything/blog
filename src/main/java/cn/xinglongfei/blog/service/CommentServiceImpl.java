package cn.xinglongfei.blog.service;

import cn.xinglongfei.blog.dao.CommentRepository;
import cn.xinglongfei.blog.po.Blog;
import cn.xinglongfei.blog.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Phoenix on 2020/11/19
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Integer countCommentByBlog(Blog blog){
        return commentRepository.countCommentByBlog(blog);
    }

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = Sort.by(Sort.Direction.ASC,"createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId,sort);
        return eachComment(comments);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if(parentCommentId!= -1){
            comment.setParentComment(commentRepository.getOne(parentCommentId));
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    @Override
    public String getLastAvatar(String email) {
        List<Comment> commentsList = new ArrayList<>();
        commentsList = commentRepository.findByEmail(email);
        if (null != commentsList && commentsList.size() > 0) {
            return commentsList.get(0).getAvatar();
        }else{
            return null;
        }
    }

    @Override
    public Comment getComment(Long id) {
        return commentRepository.getOne(id);
    }

    /**
     * 循环获取每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment:comments){
            Comment commentTemp = new Comment();
            BeanUtils.copyProperties(comment,commentTemp);
            commentsView.add(commentTemp);
        }
        //合并评论的各层孩子到第一级孩子中
        combineChildren(commentsView);
        return commentsView;
    }


    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     */
    private void combineChildren(List<Comment> comments){
        for (Comment comment:comments){
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys){
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }


    //存放迭代找出所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     */
    private void recursively(Comment comment){
        tempReplys.add(comment);
        if(comment.getReplyComments().size()>0){
            List<Comment> replys = comment.getReplyComments();
            for(Comment reply : replys){
                tempReplys.add(reply);
                if(reply.getReplyComments().size()>0){
                    recursively(reply);
                }
            }
        }
    }



}
