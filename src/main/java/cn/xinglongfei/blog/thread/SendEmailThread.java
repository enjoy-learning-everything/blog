package cn.xinglongfei.blog.thread;

import cn.xinglongfei.blog.po.Comment;
import cn.xinglongfei.blog.util.EmailContentUtil;
import cn.xinglongfei.blog.util.EmailSendUtil;

import java.io.File;

/**
 * Created by Phoenix on 2020/12/30
 */
public class SendEmailThread implements Runnable{

    private Comment comment;

    private String blogAuthorEmail;

    private String blogIndex;

    public SendEmailThread() {
    }

    public SendEmailThread(Comment comment, String blogAuthorEmail, String blogIndex) {
        this.comment = comment;
        this.blogAuthorEmail = blogAuthorEmail;
        this.blogIndex = blogIndex;
    }

    public String getBlogAuthorEmail() {
        return blogAuthorEmail;
    }

    public void setBlogAuthorEmail(String blogAuthorEmail) {
        this.blogAuthorEmail = blogAuthorEmail;
    }

    public String getBlogIndex() {
        return blogIndex;
    }

    public void setBlogIndex(String blogIndex) {
        this.blogIndex = blogIndex;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    @Override
    public void run() {
        try {
            Comment commentParent = comment.getParentComment();
            String templateRelativePath = "templates"+ File.separator+"email-templates"+File.separator;
            if(commentParent==null){
                //如果是评论根节点，给博主发邮件
                String template = EmailContentUtil.readToString(templateRelativePath+"newComment.html");
                EmailSendUtil.sendEmail(blogAuthorEmail,"您的 【小破站】 上有新的评论啦！",
                        EmailContentUtil.setNewCommentEmailContent(template,comment.getNickname(),comment.getContent(),blogIndex+"/blogs/"+comment.getBlog().getId()),true);
            }else{
                //如果不是评论根节点，给被回复者发邮件
                String template = EmailContentUtil.readToString(templateRelativePath+"newCommentReply.html");
                EmailSendUtil.sendEmail(commentParent.getEmail(),"您在 【小破站】 上的评论有新的回复啦！",
                        EmailContentUtil.setNewCommentReplyEmailContent(template,commentParent.getNickname(),commentParent.getContent(),
                                comment.getNickname(),comment.getContent(),blogIndex+"/blogs/"+comment.getBlog().getId()),true);
            }
        } catch (Exception e) {
            System.out.println("邮件发送异常");
            e.printStackTrace();
        }
    }
}
